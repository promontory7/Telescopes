package com.xuhai.telescopes.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuhai.easeui.ui.EaseBaseFragment;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.NestListRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by chudong on 2015/11/21.
 */
public class SeaFragment extends EaseBaseFragment {

    private RecyclerView recyclerView ;

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
//        setupRecyclerView(recyclerView);

    }

    @Override
    protected void setUpView() {

    }

//    private void setupRecyclerView(RecyclerView recyclerView) {
//        ArrayList<String> title = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            title.add("我想打造一个殿堂级团队");
//        }
//
//        ArrayList<String> content = new ArrayList<>();
//        for (int j = 0; j < 5; j++) {
//            content.add("简介：自盘古开天辟地以来，世间缺少这么一个人...");
//        }
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
//        NestListRecyclerViewAdapter nestListRecyclerViewAdapter = new NestListRecyclerViewAdapter(getContext(), nets);
//        nestListRecyclerViewAdapter.setOnItemClickListener(new NestListRecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Log.e("点击", position + "");
////                startActivity(new Intent(this,CreateNestActivity.class));
//
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
////                Toast.makeText(this, "长安啦" + position, Toast.LENGTH_LONG).show();
//
//            }
//        });
//        recyclerView.setAdapter(nestListRecyclerViewAdapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
