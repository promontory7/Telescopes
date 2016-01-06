package com.xuhai.telescopes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.domain.MatchingNet;
import com.xuhai.telescopes.domain.MatchingRole;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chudong on 2015/12/29.
 */
public class NetMarketAdapter extends BaseExpandableListAdapter {

    private ArrayList<MatchingRole> matchingRoles;
    private Context context;


    public NetMarketAdapter(Context context, ArrayList<MatchingRole> matchingRoles) {
        this.context = context;
        this.matchingRoles = matchingRoles;
    }

    @Override
    public int getGroupCount() {
        return matchingRoles != null ? matchingRoles.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return matchingRoles.get(groupPosition).getMatchingNets() != null ?
                matchingRoles.get(groupPosition).getMatchingNets().size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return matchingRoles.get(groupPosition) != null ? matchingRoles.get(groupPosition) : null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return matchingRoles.get(groupPosition).getMatchingNets().get(childPosition) != null ?
                matchingRoles.get(groupPosition).getMatchingNets().get(childPosition) : null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentHolder parentHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_market_parent, parent,false);
            parentHolder = new ParentHolder(convertView);
            convertView.setTag(parentHolder);
        } else {
            parentHolder = (ParentHolder) convertView.getTag();
        }
        String role_name= (matchingRoles.get(groupPosition).getSeaman_role_name());
        parentHolder.role_name.setText("海员："+role_name);
        parentHolder.number.setText("(" + matchingRoles.get(groupPosition).getMatchingNets().size()+ ")");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_market_child, parent,false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        MatchingNet matchingNet = matchingRoles.get(groupPosition).getMatchingNets().get(childPosition);
        childHolder.task.setText(matchingNet.getTask());
        childHolder.userName.setText(matchingNet.getUser_name());
        if (matchingNet.getIs_read() == "1") {
            childHolder.is_read.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ParentHolder {
        TextView role_name;
        TextView number;

        ParentHolder(View view) {
            role_name= (TextView) view.findViewById(R.id.item_market_parent_role_name);
            number = (TextView) view.findViewById(R.id.item_market_parent_number);
        }
    }

    class ChildHolder {
        CircleImageView avater;
        TextView task;
        TextView userName;
        ImageView is_read;

        ChildHolder(View view) {
            avater = (CircleImageView) view.findViewById(R.id.market_child_avater);
            task = (TextView) view.findViewById(R.id.market_child_task);
            userName = (TextView) view.findViewById(R.id.market_child_userName);
            is_read = (ImageView) view.findViewById(R.id.market_child_is_read);
        }

    }
}
