package com.xuhai.telescopes.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.OceanUserCommentAdapter;
import com.xuhai.telescopes.httpclient.HttpUtil;
import com.xuhai.telescopes.model.OceanCommentModel;
import com.xuhai.telescopes.model.OceanModel;
import com.xuhai.telescopes.model.OceanUserModel;
import com.xuhai.telescopes.model.UserCommentModel;
import com.xuhai.telescopes.widget.PullSeparateListView;
import com.xuhai.telescopes.widget.TextWithImage;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * @author 大海单个用户所有评论
 * @date 2015/11/15 0015.
 */
public class OceanCommentActivity extends BaseActivity implements OnClickListener{
    private ImageView leftImage;
    private TextView titleText;
    private ImageView rightImage;

    public PullSeparateListView listView;
    public OceanUserCommentAdapter adapter;

    public ImageView headImage;
    public TextView nameText;
    public TextView levelText;
    public TextView effectText;
    public TextView linkText;

    private ArrayList<OceanCommentModel> list = new ArrayList<OceanCommentModel>();
    private int userId;
    private int oceanId;
    public void onCreate(Bundle onsavedInstanceState){
        super.onCreate(onsavedInstanceState);
        setContentView(R.layout.activity_ocean_comment);
        findView();
        setContent();
        setListener();
    }

    public void findView(){

        leftImage = (ImageView)findViewById(R.id.iv_left);
        titleText = (TextView)findViewById(R.id.tv_title);
        rightImage = (ImageView)findViewById(R.id.iv_right);
        headImage = (ImageView)findViewById(R.id.iv_head);
        nameText = (TextView)findViewById(R.id.tv_name);
        levelText = (TextView)findViewById(R.id.tv_level);
        effectText = (TextView)findViewById(R.id.tv_effect);
        linkText = (TextView)findViewById(R.id.tv_link);
        listView = (PullSeparateListView)findViewById(R.id.lv_comment);
        adapter = new OceanUserCommentAdapter(this);
        listView.setAdapter(adapter);
    }

    public void setContent(){
        titleText.setText("个人评论");
        userId = getIntent().getIntExtra("userId",-1);
        oceanId = getIntent().getIntExtra("oceanId",-1);
        HttpUtil.getInstance().getOceanUserComment(this, userId, oceanId, getuserCommentListener);
        HttpUtil.getInstance().getOceanLink(this, userId, oceanId, getOceanLinkListener);
    }

    public void setUserInfo(OceanUserModel userModel){
        nameText.setText(userModel.nickname);
    }
    public void setListener(){
        leftImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case R.id.iv_left:
            finish();
            break;
        }
    }

    private JsonHttpResponseHandler getuserCommentListener = new JsonHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            if (statusCode == 200) {
                Log.i("getlist", response.toString());
                try {
                     list = OceanCommentModel.getListNoLevel(response.getString("data"));
                    adapter.setList(list);
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

    private JsonHttpResponseHandler getOceanLinkListener = new JsonHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            if (statusCode == 200) {
                Log.i("getlist", response.toString());
                try {
                    OceanModel model = new OceanModel(response.getString("data"));
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
