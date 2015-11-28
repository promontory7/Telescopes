
package com.xuhai.telescopes.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuhai.easeui.domain.EaseUser;
import com.xuhai.easeui.utils.EaseUserUtils;
import com.xuhai.telescopes.MyHelper;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.widget.PinnedHeaderExpandableListView;
import com.xuhai.telescopes.widget.PinnedHeaderExpandableListView.HeaderAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by chudong on 2015/10/30.
 */
public class PinnedHeaderExpandableAdapter extends BaseExpandableListAdapter implements HeaderAdapter {
    private Map<String, List<String>> childrenData;
    private ArrayList<String> groupData;
    private Context context;
    private PinnedHeaderExpandableListView listView;
    private LayoutInflater inflater;

    public PinnedHeaderExpandableAdapter(Map<String, List<String>> childrenData, ArrayList<String> groupData
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
        ImageView avatar_view = (ImageView) view.findViewById(R.id.avatar);
        TextView username_view = (TextView) view.findViewById(R.id.username);
        TextView signature_view = (TextView) view.findViewById(R.id.signature);

        String key = groupData.get(groupPosition);
        String username = childrenData.get(key).get(childPosition);
        EaseUser user = MyHelper.getInstance().getContactList().get(username);
        EaseUserUtils.setUserAvatar(context, username, avatar_view);
        username_view.setText(user.getUsername());
        signature_view.setText(user.getSignature());

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

        ImageView iv = (ImageView) view.findViewById(R.id.toggle_fenzu);

        if (isExpanded) {
            iv.setImageResource(R.drawable.my_row_friendslist_open_fenzu);
        } else {
            iv.setImageResource(R.drawable.my_row_friendslist_close_fenzu);
        }

        TextView text = (TextView) view.findViewById(R.id.name_fenzu);
        text.setText(groupData.get(groupPosition));
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
        return inflater.inflate(R.layout.my_row_friendslist_child, null);
    }

    private View createGroupView() {
        return inflater.inflate(R.layout.em_row_group, null);
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
        ((TextView) header.findViewById(R.id.name_fenzu)).setText(groupData);

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
