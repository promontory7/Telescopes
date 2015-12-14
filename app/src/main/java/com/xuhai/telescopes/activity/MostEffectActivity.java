package com.xuhai.telescopes.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.EffectListAdapter;
import com.xuhai.telescopes.httpclient.HttpUtil;
import com.xuhai.telescopes.model.OceanUserModel;
import com.xuhai.telescopes.utils.DialogUtil;
import com.xuhai.telescopes.utils.NotificationUtil;
import com.xuhai.telescopes.utils.ToastUtils;
import com.xuhai.telescopes.widget.PullSeparateListView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * 评选最具影响力
 * @author LHB
 * @date 2015/11/5 0005.
 */
public class MostEffectActivity extends BaseActivity implements OnClickListener{

    public ImageView leftImage;
    public TextView titleText;
    public TextView rightText;

    private PullSeparateListView listview;
    private EffectListAdapter adapter;

    private ArrayList<OceanUserModel> userList = new ArrayList<OceanUserModel>();

    private int userId;
    private int oceanId;
    private NotificationUtil notificationUtil;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_effect);
        findViews();
        setContent();
        setListener();
    }

    public void findViews( ){
        leftImage = (ImageView) findViewById(R.id.iv_left);
        titleText = (TextView) findViewById(R.id.tv_title);
        rightText = (TextView) findViewById(R.id.tv_right);
        listview = (PullSeparateListView)findViewById(R.id.effect_list);
    }

    public void setContent(){
        oceanId = getIntent().getIntExtra("oceanId",-1);
        rightText.setVisibility(View.VISIBLE);
        rightText.setOnClickListener(this);
        leftImage.setOnClickListener(this);
        titleText.setText("评选最具影响力");
        adapter = new EffectListAdapter(this);
        listview.setAdapter(adapter);
        //notificationUtil = new NotificationUtil(this,6666);

        HttpUtil.getInstance().getOceanEffectList(this, oceanId, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (statusCode == 200) {
                    try {
                        userList = OceanUserModel.getList(response.getString("data"));
                        adapter.setList(userList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
    public void setListener(){
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedPosition(position);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                DialogUtil.dimissDialog();
                DialogUtil.setTips(this,"是否不进行评选，直接退出？","再看看","退出" ,new OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.button_left:
                                DialogUtil.dimissDialog();
                                break;
                            case R.id.button_right:
                                finish();
                                DialogUtil.dimissDialog();
                                break;
                        }
                    }
                });
               // finish();
                break;
            case R.id.tv_right:
                rightText.setEnabled(false);
                HttpUtil.getInstance().selectMostEffectUser(this, adapter.getselectedUserId(), oceanId, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        rightText.setEnabled(true);
                        ToastUtils.show(MostEffectActivity.this, "评选成功");
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        rightText.setEnabled(true);
                    }
                });
                break;
        }
    }
}
