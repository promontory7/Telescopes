package com.xuhai.telescopes.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 大海切换界面
 * @author LHB
 * @date 2015/10/22 0022.
 */
public class OceanPagerAdapter extends PagerAdapter {

    private Context context;
    private int TabNum = 2;
    private ArrayList<View> pageViews;

    public OceanPagerAdapter(Context context,ArrayList<View> pageViews){
        this.context = context;
        this.pageViews = pageViews;

    }
    @Override
    public int getCount() {
        return pageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView(pageViews.get(position));
    }
    @Override
    public View instantiateItem(ViewGroup view, int position){

        view.addView(pageViews.get(position));
        return pageViews.get(position);
    }

}
