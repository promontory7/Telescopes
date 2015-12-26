package com.xuhai.telescopes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.xuhai.easeui.ui.EaseBaseFragment;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.OceanListAdapter;
import com.xuhai.telescopes.adapter.OceanPagerAdapter;
import com.xuhai.telescopes.httpclient.HttpUtil;
import com.xuhai.telescopes.model.OceanModel;
import com.xuhai.telescopes.widget.listview.XListView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 大海列表
 */
public class OceanFramgent extends EaseBaseFragment implements View.OnClickListener {

    private ViewPager viewpager;
    private OceanPagerAdapter pagerAdapter;
    private View privateLayout;
    private View publicLayout;
    private View privateBottom;
    private View publicBottom;
    private ImageView rightImage;
    private Context context;
    private int position;
    private ArrayList<View> pageViews = new ArrayList<View>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = this.getActivity();
        return inflater.inflate(R.layout.fragment_ocean, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView() {
        View layout = this.getView();

        privateLayout = layout.findViewById(R.id.ll_private_ocean);
        publicLayout = layout.findViewById(R.id.ll_public_ocean);
        privateBottom = layout.findViewById(R.id.view_private_bottom);
        publicBottom = layout.findViewById(R.id.view_public_bottom);
        rightImage = (ImageView) layout.findViewById(R.id.iv_add);

        viewpager = (ViewPager) layout.findViewById(R.id.vp_ocean);
    }

    public void setCurrentTab(int position) {
        if (position == 0) {
            privateBottom.setVisibility(View.VISIBLE);
            publicBottom.setVisibility(View.GONE);
            this.position = 0;
        } else {
            privateBottom.setVisibility(View.GONE);
            publicBottom.setVisibility(View.VISIBLE);
            this.position = 1;
        }

    }

    @Override
    protected void setUpView() {
        {
            for (int i = 0; i < 2; i++) {
                View view = LayoutInflater.from(this.getActivity()).inflate(R.layout.item_viewpager_ocean, null);
                pageViews.add(view);
            }
            pagerAdapter = new OceanPagerAdapter(this.getActivity(), pageViews);
            viewpager.setAdapter(pagerAdapter);
            rightImage.setOnClickListener(this);
            privateLayout.setOnClickListener(this);
            publicLayout.setOnClickListener(this);
            viewpager.setOnPageChangeListener(pageChangeListener);
            pageChangeListener.onPageSelected(0);
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            Log.i("Tab", "pageScrolled=" + position);
        }

        @Override
        public void onPageSelected(int position) {
//            Log.i("Tab", "onPageSelected=" + position);
            setCurrentTab(position);
            Log.i("Tab", "onPageScrollStateChanged=" + position);

            OceanManage oceanManage;
            View view = pageViews.get(position);
            if (view.getTag() == null) {
                oceanManage = new OceanManage();
                oceanManage.findViews(view);
                oceanManage.setContent(position);
                oceanManage.setListener();
                view.setTag(oceanManage);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
//            Log.i("Tab", "onPageScrollStateChanged=" + state);

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                Intent intent2 = new Intent(getActivity(), OceanPublishActivityNew.class);
                getActivity().startActivity(intent2);
                break;
            case R.id.ll_private_ocean:
                if (position != 0)
                    viewpager.setCurrentItem(0);
                break;
            case R.id.ll_public_ocean:
                if (position != 1)
                    viewpager.setCurrentItem(1);
                break;
        }
    }

    public class OceanManage {
        XListView list;
        OceanListAdapter listAdapter;
        private int position;

        public void findViews(View view) {
            list = (XListView) view.findViewById(R.id.list_ocean);
        }

        public void setContent(int position) {
            this.position = position;
            listAdapter = new OceanListAdapter(context);
            list.setAdapter(listAdapter);
            HttpUtil.getInstance().getOceanTopicList(context, position, getListListener);
        }

        public void setListener() {
            list.setEnabled(true);
            list.setXListViewListener(new XListView.IXListViewListener() {
                @Override
                public void onRefresh() {
                    HttpUtil.getInstance().getOceanTopicList(context, position, getListListener);
                }

                @Override
                public void onLoadMore() {
                    list.stopLoadMore();
                }
            });
        }

        private JsonHttpResponseHandler getListListener = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (statusCode == 200) {
                    Log.i("getlist", response.toString());
                    try {
                        ArrayList<OceanModel> tempList = OceanModel.getList(response.getString("data"));
                        listAdapter.setList(tempList);
                        list.stopRefresh();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                list.stopRefresh();
            }
        };
    }
}
