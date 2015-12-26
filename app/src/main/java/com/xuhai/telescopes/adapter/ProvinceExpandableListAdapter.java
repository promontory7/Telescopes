package com.xuhai.telescopes.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.entity.CityEntity;
import com.xuhai.telescopes.entity.ProvinceEntity;
import com.xuhai.telescopes.widget.PinnedHeaderExpandableListView;

import java.util.ArrayList;

/**
 * Created by chudong on 2015/12/5.
 */
public class ProvinceExpandableListAdapter extends BaseExpandableListAdapter implements PinnedHeaderExpandableListView.HeaderAdapter {

    private Context context;
    private ArrayList<ProvinceEntity> provinces;
    private PinnedHeaderExpandableListView pinnedHeaderExpandableListView;
    private LayoutInflater inflater;
    private OnChildTreeViewClickListener mTreeViewClickListener;// 点击子ExpandableListView子项的监听

    public ProvinceExpandableListAdapter(Context context, ArrayList<ProvinceEntity> provinces
            , PinnedHeaderExpandableListView pinnedHeaderExpandableListView) {
        this.context = context;
        this.provinces = provinces;
        this.pinnedHeaderExpandableListView = pinnedHeaderExpandableListView;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getGroupCount() {
        return provinces != null ? provinces.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return provinces.get(groupPosition).getCitys() != null ?
                provinces.get(groupPosition).getCitys().size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return provinces.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return provinces.get(groupPosition).getCitys().get(childPosition);
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ProvinceViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_schoolpicker_province, null);
            holder = new ProvinceViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ProvinceViewHolder) convertView.getTag();
        }
        holder.updataTextView(provinces.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, final View convertView, ViewGroup parent) {

        final ExpandableListView expandableListView = getExpandableListView();
        ArrayList<CityEntity> cityEntities = new ArrayList<CityEntity>();
        final CityEntity cityEntity = (CityEntity) getChild(groupPosition, childPosition);
        cityEntities.add(cityEntity);
        expandableListView.setAdapter(new CityExpandableListAdapter(context, cityEntities));
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (mTreeViewClickListener != null) {
                    mTreeViewClickListener.onClickPosition(groupPosition,
                            childPosition, childPosition);
                }
                return false;
            }
        });

        /**
         * 子ExpandableListView展开时，因为group只有一项，所以子ExpandableListView的总高度=
         * （子ExpandableListView的child数量 + 1 ）* 每一项的高度
         * */
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, (cityEntity
                        .getSchool().size() + 1)
                        * (int) context.getResources().getDimension(
                        R.dimen.item_picker_height));
                expandableListView.setLayoutParams(lp);
            }
        });

        /**
         * 子ExpandableListView关闭时，此时只剩下group这一项，
         * 所以子ExpandableListView的总高度即为一项的高度
         * */
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, (int) context
                        .getResources().getDimension(
                                R.dimen.item_picker_height));
                expandableListView.setLayoutParams(lp);
            }
        });
        return expandableListView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class ProvinceViewHolder {
        private TextView textView;

        public ProvinceViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.textview);
        }

        public void updataTextView(ProvinceEntity provinceEntity) {
            textView.setText(provinceEntity.getProvince_name());
        }
    }

    /**
     * 动态创建子ExpandableListView
     */
    public ExpandableListView getExpandableListView() {
        ExpandableListView mExpandableListView = new ExpandableListView(
                context);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) context.getResources().getDimension(R.dimen.item_picker_height));
        mExpandableListView.setLayoutParams(lp);
//        mExpandableListView.setDividerHeight(R.dimen.item_picker_devider_height);// 取消group项的分割线
//        mExpandableListView.setChildDivider(null);// 取消child项的分割线
        mExpandableListView.setGroupIndicator(null);// 取消展开折叠的指示图标
        return mExpandableListView;
    }

    /**
     * 设置点击子ExpandableListView子项的监听
     */
    public void setOnChildTreeViewClickListener(
            OnChildTreeViewClickListener treeViewClickListener) {
        this.mTreeViewClickListener = treeViewClickListener;
    }

    /**
     * 点击子ExpandableListView子项的回调接口
     */
    public interface OnChildTreeViewClickListener {

        void onClickPosition(int parentPosition, int groupPosition,
                             int childPosition);
    }


    //-----------------------------------HeaderAdapter----------------

    @Override
    public int getHeaderState(int groupPosition, int childPosition) {
        final int childCount = getChildrenCount(groupPosition);
        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
        } else if (childPosition == -1
                && !pinnedHeaderExpandableListView.isGroupExpanded(groupPosition)) {
            return PINNED_HEADER_GONE;
        } else {
            return PINNED_HEADER_VISIBLE;
        }
    }

    @Override
    public void configureHeader(View header, int groupPosition, int childPosition, int alpha) {
        String groupData = this.provinces.get(groupPosition).getProvince_name();
        ((TextView) header.findViewById(R.id.textview)).setText(groupData);

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
