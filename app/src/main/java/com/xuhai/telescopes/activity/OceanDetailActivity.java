package com.xuhai.telescopes.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.loopj.android.http.JsonHttpResponseHandler;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.OceanCommentAdapter;
import com.xuhai.telescopes.httpclient.HttpUtil;
import com.xuhai.telescopes.model.OceanCommentModel;
import com.xuhai.telescopes.model.OceanDetailModel;
import com.xuhai.telescopes.model.OceanModel;
import com.xuhai.telescopes.utils.ImageUtils;
import com.xuhai.telescopes.utils.TimeUtil;
import com.xuhai.telescopes.utils.ToastUtils;
import com.xuhai.telescopes.widget.CircleImageView;
import com.xuhai.telescopes.widget.TextWithImage;
import com.xuhai.telescopes.widget.listview.XListView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 大海详情
 * @author LHB
 * @date 2015/11/12 0012.
 */
public class OceanDetailActivity extends BaseActivity implements OnClickListener{

    private ImageView leftImage;
    private TextView titleText;
    private ImageView rightImage;

    public XListView listView;
    public OceanCommentAdapter adapter;

    public ImageView headImage;
    public TextView timeText;
    public TextView nameText;
    public TextWithImage contentText;

    public EditText chatEdit;
    public Button chatSend;

    public int selectedPosition = 0;

    public boolean isAccount = false;

    public ArrayList<OceanCommentModel> commentList;
    private int oceanId;
    public void onCreate(Bundle onsavedInstanceState){
        super.onCreate(onsavedInstanceState);
        setContentView(R.layout.activity_ocean_detail);
        findView();
        findHeadView();
        setContent();
        setListener();
    }

    public void findView(){

        leftImage = (ImageView)findViewById(R.id.iv_left);
        titleText = (TextView)findViewById(R.id.tv_title);
        rightImage = (ImageView)findViewById(R.id.iv_right);
        listView = (XListView)findViewById(R.id.lv_ocean);

        chatEdit = (EditText)findViewById(R.id.chat_content);
        chatSend = (Button)findViewById(R.id.chat_send);
    }

    public void findHeadView(){
        View view = LayoutInflater.from(this).inflate(R.layout.item_list_head_ocean,null);
        headImage =(ImageView)view.findViewById(R.id.iv_head);
        timeText = (TextView)view.findViewById(R.id.tv_time);
        nameText = (TextView)view.findViewById(R.id.tv_name);
        contentText = (TextWithImage)view.findViewById(R.id.tv_content);
        listView.addHeaderView(view);
        adapter = new OceanCommentAdapter(this);
        listView.setAdapter(adapter);
    }

    public void setContent(){
        titleText.setText("大海详情");
        oceanId = getIntent().getIntExtra("oceanId",-1);
        HttpUtil.getInstance().getOceanTopicDetail(this, oceanId, getDetailListener);

    }

    public void setOceanDetail( OceanDetailModel  model){
        timeText.setText(TimeUtil.getStringFromStr(model.created_at));
        nameText.setText(model.user.nickname);
        Drawable drawable = this.getResources().getDrawable(R.drawable.bg_test);
        headImage.setImageBitmap(ImageUtils.getRoundedCornerBitmap(drawable, 80));
        contentText.setTextContent(model.content, new ArrayList<String>());
    }

    public  void setListener(){
        headImage.setOnClickListener(this);
        leftImage.setOnClickListener(this);
        chatSend.setOnClickListener(this);
        listView.setPullLoadEnable(false);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                HttpUtil.getInstance().getOceanTopicDetail(OceanDetailActivity.this, oceanId, getDetailListener);
            }

            @Override
            public void onLoadMore() {
                listView.stopLoadMore();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                if(selectedPosition > 1)
                    chatEdit.setHint("回复 ："+commentList.get(selectedPosition-2).user.nickname);
                else
                    chatEdit.setHint("评论");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.iv_head:
                Intent intent = new Intent(this,UserCardActivity.class);
                startActivity(intent);
                break;
            case R.id.chat_send:
                String temp = chatEdit.getText().toString();
                if(TextUtils.isEmpty(temp)){
                    ToastUtils.show(this,"评论内容不能为空");
                    return;
                }
                int commentId = -1;
                if (selectedPosition>1){
                    commentId = commentList.get(selectedPosition-2).comment_id;
                }else{
                    commentId = -1;
                }
                Log.i("commentId","commentId = "+commentId);
                HttpUtil.getInstance().addOceanTopicComment(this, commentId, oceanId, temp,new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        if (statusCode == 200) {
                            ToastUtils.show(OceanDetailActivity.this,"评论成功");
                            chatEdit.setText("");
                            Log.i("getlist", response.toString());
                            listView.stopRefresh();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        listView.stopRefresh();
                    }
                });
        }
    }

    private JsonHttpResponseHandler getDetailListener = new JsonHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            if (statusCode == 200) {
                Log.i("getlist", response.toString());
                try {
                    OceanDetailModel model = new OceanDetailModel(response.getString("data"));
                    HttpUtil.getInstance().getOceanTopicComment(OceanDetailActivity.this, oceanId, getCommentListener);
                    setOceanDetail(model);
                    listView.stopRefresh();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            listView.stopRefresh();
        }
    };

    private JsonHttpResponseHandler getCommentListener = new JsonHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            if (statusCode == 200) {
                Log.i("getlist", response.toString());
                try {
                    commentList = OceanCommentModel.getList(response.getString("data"));
                    adapter.setList(commentList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };
}
