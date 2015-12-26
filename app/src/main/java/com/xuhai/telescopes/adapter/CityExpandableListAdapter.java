package com.xuhai.telescopes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.entity.CityEntity;

import java.util.ArrayList;

/**
 * Created by chudong on 2015/12/5.
 */
public class CityExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    ArrayList<CityEntity> cityEntities = new ArrayList<CityEntity>();

    public CityExpandableListAdapter(Context context, ArrayList<CityEntity> cityEntities) {
        this.context = context;
        this.cityEntities = cityEntities;
    }

    @Override
    public int getGroupCount() {
        return cityEntities != null ? cityEntities.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return cityEntities.get(groupPosition).getSchool() != null ?
                cityEntities.get(groupPosition).getSchool().size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (cityEntities != null && cityEntities.size() > 0) {
            return cityEntities.get(groupPosition);
        }
        return null;
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        if (cityEntities.get(groupPosition).getSchool().get(childPosition) != null &&
                cityEntities.get(groupPosition).getSchool().size() > 0) {
            return cityEntities.get(childPosition).getSchool().get(childPosition).toString();
        }
        return null;
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
        CityViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_schoolpicker_city, null);
            holder = new CityViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CityViewHolder) convertView.getTag();
        }
        holder.updataTextview(cityEntities.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SchoolViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_schoolpicker_school, null);
            holder = new SchoolViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (SchoolViewHolder) convertView.getTag();
        }
        holder.updataTextview(cityEntities.get(groupPosition).getSchool().get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



    class CityViewHolder {
        private TextView textView;

        public CityViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.textview);
        }

        public void updataTextview(CityEntity cityEntity) {
            textView.setText(cityEntity.getCity_name());
        }
    }

    class SchoolViewHolder {
        private TextView textView;

        public SchoolViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.textview);
        }

        public void updataTextview(String school) {
            textView.setText(school);
        }
    }
}
