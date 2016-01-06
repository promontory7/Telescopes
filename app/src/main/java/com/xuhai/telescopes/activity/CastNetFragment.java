package com.xuhai.telescopes.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xuhai.easeui.ui.EaseBaseFragment;
import com.xuhai.telescopes.MyApplication;
import com.xuhai.telescopes.MyHelper;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.NestListRecyclerViewAdapter;
import com.xuhai.telescopes.domain.Net;
import com.xuhai.telescopes.httpclient.HttpUtil;
import com.xuhai.telescopes.httpclient.httpResponseHandle.AsyncNetsListFromServer;
import com.xuhai.telescopes.httpclient.httpResponseHandle.BaseJsonHttpResponseHandle;
import com.xuhai.telescopes.utils.DialogUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chudong on 2015/11/21.
 */
public class CastNetFragment extends EaseBaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NestListRecyclerViewAdapter nestListRecyclerViewAdapter;
    private ArrayList<Net> nets = new ArrayList<Net>();
    private Context context;

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
        context = getContext();
        recyclerView = (RecyclerView) getView().findViewById(R.id.net_recyclerView);

        swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

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

        setupRecyclerView(recyclerView);
        refreshFromServer();
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
                        Intent intent = new Intent(getContext(), NetDetailActivity.class);
                        intent.putExtra("net", nets.get(position));
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, final int position) {

                        DialogUtil.dimissDialog();
                        DialogUtil.setOceanOpreateDialog((Activity) context, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.btn_01:
//                                        //收网
                                        DialogUtil.dimissDialog();
                                        break;
                                    case R.id.btn_06:
                                        //删除
                                        HttpUtil.getInstance().delectNet(context, nets.get(position).getId(), new BaseJsonHttpResponseHandle() {

                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                super.onSuccess(statusCode, headers, response);
                                                MyHelper.getInstance().deleteNet(nets.get(position).getId());
                                                Toast.makeText(context,"删除成功",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        DialogUtil.dimissDialog();
                                        nets.remove(position);
                                        nestListRecyclerViewAdapter.notifyDataSetChanged();

                                        break;
                                    case R.id.btn_07:
                                        DialogUtil.dimissDialog();
                                        break;
                                }
                            }
                        });
                    }
                });
        recyclerView.setAdapter(nestListRecyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void refreshFromServer() {
        HttpUtil.getInstance().asyncgetNetsList(MyApplication.applicationContext, new AsyncNetsListFromServer() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (statusCode == 200) {
                    Log.e("asyncNetsListFromServer", "成功啦");
                    nets.clear();
                    nets.addAll(MyHelper.getInstance().getNetslist());
                    Log.e("nets", nets.toString());

                    nestListRecyclerViewAdapter.notifyDataSetChanged();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        refreshFromServer();
    }
}
