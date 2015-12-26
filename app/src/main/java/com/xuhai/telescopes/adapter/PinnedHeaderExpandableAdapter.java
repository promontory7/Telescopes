package com.xuhai.telescopes.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.widget.PinnedHeaderExpandableListView;
import com.xuhai.telescopes.widget.PinnedHeaderExpandableListView.HeaderAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PinnedHeaderExpandableAdapter extends BaseExpandableListAdapter implements HeaderAdapter {
    private HashMap<String, List<String>> childrenData;
    private ArrayList<String> groupData;

    private Context context;
    private PinnedHeaderExpandableListView listView;
    private LayoutInflater inflater;

    public PinnedHeaderExpandableAdapter(ArrayList<String> groupData, HashMap<String, List<String>> childrenData
            , Context context, PinnedHeaderExpandableListView listView) {
        this.groupData = groupData;
        this.childrenData = childrenData;
        this.context = context;
        this.listView = listView;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String key = groupData.get(groupPosition);
        return childrenData.get(key).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = createChildrenView();
        }
        TextView childView = (TextView) view.findViewById(R.id.child_name);

        String key = groupData.get(groupPosition);
        final String cityName = childrenData.get(key).get(childPosition);

        childView.setText(cityName);

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupData != null) {
            String key = groupData.get(groupPosition);
            if (childrenData.get(key) != null) {
                return childrenData.get(key).size();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        if (groupData != null) {
            return groupData.size();
        } else {
            return 0;
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = createGroupView();
        }
        TextView groupView = (TextView) view.findViewById(R.id.group_name);

//        if (isExpanded) {
//            iv.setImageResource(R.drawable.open_fenzu);
//        } else {
//            iv.setImageResource(R.drawable.close_fenzu);
//        }
        groupView.setText(groupData.get(groupPosition));
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private View createChildrenView() {
        return inflater.inflate(R.layout.item_citypicker_child, null);
    }

    private View createGroupView() {
        return inflater.inflate(R.layout.item_citypicker_group, null);
    }

    @Override
    public int getHeaderState(int groupPosition, int childPosition) {
        final int childCount = getChildrenCount(groupPosition);
        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
        } else if (childPosition == -1
                && !listView.isGroupExpanded(groupPosition)) {
            return PINNED_HEADER_GONE;
        } else {
            return PINNED_HEADER_VISIBLE;
        }
    }

    @Override
    public void configureHeader(View header, int groupPosition,
                                int childPosition, int alpha) {
        String groupData = this.groupData.get(groupPosition);
        ((TextView) header.findViewById(R.id.header_name)).setText(groupData);

    }

    private SparseIntArray groupStatusMap = new SparseIntArray();

    @Override
    public void setGroupClickStatus(int groupPosition, int status) {
        groupStatusMap.put(groupPosition, status);
    }

    @Override
    public int getGroupClickStatus(int groupPosition) {
        if (groupStatusMap.keyAt(groupPosition) >= 0) {
            return groupStatusMap.get(groupPosition);
        } else {
            return 0;
        }
    }
}
