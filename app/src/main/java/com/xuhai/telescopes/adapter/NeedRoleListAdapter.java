package com.xuhai.telescopes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.domain.Seaman;

import java.util.ArrayList;

/**
 * Created by chudong on 2015/12/28.
 */
public class NeedRoleListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Seaman> role_list;

    public NeedRoleListAdapter(Context context, ArrayList<Seaman> role_list) {
        this.context = context;
        this.role_list = role_list;
    }

    @Override
    public int getCount() {
        return role_list.size();
    }

    @Override
    public Object getItem(int position) {
        return role_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_need_role, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.role_name_tv.setText(role_list.get(position).getSeaman_role_name());
        return convertView;
    }

    class ViewHolder {
        TextView role_name_tv;

        public ViewHolder(View view) {
            role_name_tv = (TextView) view.findViewById(R.id.net_role_name_tv);
        }
    }
}
