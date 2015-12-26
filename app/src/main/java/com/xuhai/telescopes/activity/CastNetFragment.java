package com.xuhai.telescopes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuhai.easeui.ui.EaseBaseFragment;
import com.xuhai.telescopes.MyApplication;
import com.xuhai.telescopes.MyHelper;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.NestListRecyclerViewAdapter;
import com.xuhai.telescopes.domain.Net;
import com.xuhai.telescopes.httpclient.HttpUtil;
import com.xuhai.telescopes.httpclient.httpResponseHandle.AsyncNetsListFromServer;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chudong on 2015/11/21.
 */
public class CastNetFragment extends EaseBaseFragment {

    private RecyclerView recyclerView;
    NestListRecyclerViewAdapter nestListRecyclerViewAdapter;
    List<Net> nets = new ArrayList<Net>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ease_fragment_cast_net, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) getView().findViewById(R.id.net_recyclerView);
        setupRecyclerView(recyclerView);


        Log.e("asyncNetsListFromServer", "开始获得撒网数据");
        HttpUtil.getInstance().asyncgetNetsList(MyApplication.applicationContext, new AsyncNetsListFromServer() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (statusCode == 200) {
                    Log.e("asyncNetsListFromServer", "成功啦");
                    nestListRecyclerViewAdapter.notifyDataSetChanged();

                }
            }
        });
    }

    @Override
    protected void setUpView() {
        titleBar.setRightImageResource(com.xuhai.telescopes.R.drawable.em_add);
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreateNestActivity.class));
            }
        });
        titleBar.setLeftImageResource(R.drawable.icon_mao);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        nets = MyHelper.getInstance().getNetslist();

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        nestListRecyclerViewAdapter =
                new NestListRecyclerViewAdapter(getContext(), nets);
        nestListRecyclerViewAdapter.setOnItemClickListener(
                new NestListRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.e("点击", position + "");
                        startActivity(new Intent(getContext(), CreateNestActivity.class));
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
//                Toast.makeText(this, "长安啦" + position, Toast.LENGTH_LONG).show();
                    }
                });
        recyclerView.setAdapter(nestListRecyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
