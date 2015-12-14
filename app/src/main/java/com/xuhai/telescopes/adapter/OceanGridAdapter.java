package com.xuhai.telescopes.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xuhai.telescopes.R;

import java.util.ArrayList;

/**
 * @author LHB
 * @date 2015/11/4 0004.
 */
public class OceanGridAdapter extends BaseAdapter{
    private ArrayList<String> urlList;
    private Context context;

    public OceanGridAdapter(Context context,ArrayList<String> urlList){
        this.context = context;
        this.urlList = urlList;

    }
    @Override
    public int getCount() {
        return urlList.size()<3?urlList.size():3;
    }

    @Override
    public Object getItem(int position) {
        return urlList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setUrlList(ArrayList<String> urlList){
        this.urlList = urlList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.bg_test);
        imageView.setAdjustViewBounds(true);

        return imageView;
    }
}
