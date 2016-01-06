package com.xuhai.telescopes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.easemob.EMValueCallBack;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.xuhai.telescopes.Constant;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.GroupAdapter;

import java.util.List;

/**
 * Created by chudong on 2016/1/6.
 */
public class AllyFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView groupListView;
    protected List<EMGroup> grouplist;
    private GroupAdapter groupAdapter;
    private InputMethodManager inputMethodManager;

    Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            swipeRefreshLayout.setRefreshing(false);
            switch (msg.what) {
                case 0:
                    refresh();
                    break;
                case 1:
                    Toast.makeText(getContext(), com.xuhai.telescopes.R.string.Failed_to_get_group_chat_information, Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        };
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.em_fragment_groups,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        grouplist = EMGroupManager.getInstance().getAllGroups();
        groupListView = (ListView) view.findViewById(com.xuhai.telescopes.R.id.list);
        //show group list
        groupAdapter = new GroupAdapter(getContext(), 1, grouplist);
        groupListView.setAdapter(groupAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(com.xuhai.telescopes.R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(com.xuhai.telescopes.R.color.holo_blue_bright, com.xuhai.telescopes.R.color.holo_green_light,
                com.xuhai.telescopes.R.color.holo_orange_light, com.xuhai.telescopes.R.color.holo_red_light);
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                EMGroupManager.getInstance().asyncGetGroupsFromServer(new EMValueCallBack<List<EMGroup>>() {

                    @Override
                    public void onSuccess(List<EMGroup> value) {
                        handler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onError(int error, String errorMsg) {
                        handler.sendEmptyMessage(1);
                    }
                });
            }
        });

        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 进入群聊
                Intent intent = new Intent(getContext(), ChatActivity.class);
                // it is group chat
                intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
                intent.putExtra("userId", groupAdapter.getItem(position).getGroupId());
                startActivityForResult(intent, 0);

            }

        });
        groupListView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                    if (getActivity().getCurrentFocus() != null)
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });
    }
    private void refresh() {
        grouplist = EMGroupManager.getInstance().getAllGroups();
        groupAdapter = new GroupAdapter(getContext(), 1, grouplist);
        groupListView.setAdapter(groupAdapter);
        groupAdapter.notifyDataSetChanged();
    }
}
