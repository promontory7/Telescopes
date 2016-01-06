package com.xuhai.telescopes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xuhai.telescopes.MyHelper;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.NeedRoleListAdapter;
import com.xuhai.telescopes.domain.MatchingData;
import com.xuhai.telescopes.domain.Net;
import com.xuhai.telescopes.httpclient.HttpUtil;
import com.xuhai.telescopes.httpclient.httpResponseHandle.BaseJsonHttpResponseHandle;
import com.xuhai.telescopes.widget.listview.ExpandListView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import carbon.widget.Button;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chudong on 2015/12/28.
 */
public class NetDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private TextView task_tv;
    private TextView role_name_tv;
    private TextView summary_tv;
    private ExpandListView need_role_listView;
    private Button net_market;
    private CircleImageView avater;

    private Net net;
    private String net_id;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         intent = getIntent();

        setContentView(R.layout.activity_net_detail);
        initView();
        initData();

        //获得这张网的匹配信息
        if(intent.getStringExtra("net_id") == null){
            HttpUtil.getInstance().getOneNetReceiver(this, net.getId(), new BaseJsonHttpResponseHandle() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    if (statusCode == 200) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            MatchingData matchingData = setMatchingNetFromJson(data);
                            MyHelper.getInstance().saveNetMarket(net.getId(), matchingData);
                            Log.e("getOneNetReceiver", "获得匹配信息成功：" + data.toString());

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

    }

    private void initView() {
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_gf_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        task_tv = (TextView) findViewById(R.id.net_task_tv);
        role_name_tv = (TextView) findViewById(R.id.net_role_name_tv);
        summary_tv = (TextView) findViewById(R.id.net_summary_tv);
        need_role_listView = (ExpandListView) findViewById(R.id.need_role_listview);
        net_market = (Button) findViewById(R.id.net_market);
        net_market.setOnClickListener(this);
        avater = (CircleImageView) findViewById(R.id.avatar);
        avater.setOnClickListener(this);
    }

    private void initData() {

        if (intent.getParcelableExtra("net") != null) {
            net = intent.getParcelableExtra("net");

            refreshInfomation();
        }

        if (intent.getStringExtra("net_id") != null) {
            net_id = intent.getStringExtra("net_id");

            net_market.setVisibility(View.INVISIBLE);

            HttpUtil.getInstance().getOneNet(this, net_id, new BaseJsonHttpResponseHandle() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        JSONObject net_JO = response.getJSONObject("data");
                        net = setNetFromJson(net_JO);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshInfomation();

                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

//        if(adapter.getCount()>0){
//            int totalHeight = 0;
//            for (int i = 0; i <adapter.getCount(); i++) {
//                View listItem = adapter.getView(i, null, need_role_listView);
//                listItem.measure(0, 0);
//                totalHeight += listItem.getMeasuredHeight();
//            }
//            ViewGroup.LayoutParams params = need_role_listView.getLayoutParams();
//            params.height = totalHeight + (need_role_listView.getDividerHeight() * (adapter.getCount() -1));
//            need_role_listView.setLayoutParams(params);
//        }
    }
    public void refreshInfomation(){

        task_tv.setText(net.getTask());
        role_name_tv.setText(net.getSeaman_role());
        summary_tv.setText(net.getSummary());

        NeedRoleListAdapter adapter = new NeedRoleListAdapter(context,net.getSeamen());
        need_role_listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.net_market:
                Intent intent = new Intent(this, NetMarketActivity.class);
                intent.putExtra("net_id", net.getId());
                startActivity(intent);
                break;
            case R.id.avatar:
                Intent intent2 = new Intent(this, UserCardActivity.class);
                intent2.putExtra("username", net.getUsername());
                startActivity(intent2);
        }
    }
}
