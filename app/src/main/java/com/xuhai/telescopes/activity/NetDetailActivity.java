package com.xuhai.telescopes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.NeedRoleListAdapter;
import com.xuhai.telescopes.domain.Net;

import carbon.widget.Button;

/**
 * Created by chudong on 2015/12/28.
 */
public class NetDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private TextView task_tv;
    private TextView role_name_tv;
    private TextView summary_tv;
    private ListView need_role_listView;
    private Button new_message_bt;

    private Net net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_net_detail);
        initView();
        initData();
    }

    private void initData() {
        Intent intent=getIntent();
        net=intent.getParcelableExtra("net");

        task_tv.setText(net.getTask());
        role_name_tv.setText(net.getSeaman_role());
        summary_tv.setText(net.getSummary());

        Log.e("getSeamen", net.getSeamen().size() + "");
        NeedRoleListAdapter adapter =new NeedRoleListAdapter(context,net.getSeamen());
        need_role_listView.setAdapter(adapter);

        if(adapter.getCount()>0){
            int totalHeight = 0;
            for (int i = 0; i <adapter.getCount(); i++) {
                View listItem = adapter.getView(i, null, need_role_listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = need_role_listView.getLayoutParams();
            params.height = totalHeight + (need_role_listView.getDividerHeight() * (adapter.getCount() -1));
            need_role_listView.setLayoutParams(params);
        }
    }

    private void initView() {
        context =this;
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
        role_name_tv= (TextView) findViewById(R.id.net_role_name_tv);
        summary_tv = (TextView) findViewById(R.id.net_summary_tv);
        need_role_listView = (ListView) findViewById(R.id.need_role_listview);
        new_message_bt = (Button) findViewById(R.id.new_message_bt);
    }

    @Override
    public void onClick(View v) {

    }
}
