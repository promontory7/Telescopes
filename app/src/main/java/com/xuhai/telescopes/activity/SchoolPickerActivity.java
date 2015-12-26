package com.xuhai.telescopes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.PinnedHeaderExpandableAdapter;
import com.xuhai.telescopes.adapter.ProvinceExpandableListAdapter;
import com.xuhai.telescopes.entity.CityEntity;
import com.xuhai.telescopes.entity.ProvinceEntity;
import com.xuhai.telescopes.widget.PinnedHeaderExpandableListView;

import java.util.ArrayList;

/**
 * Created by chudong on 2015/12/5.
 */
public class SchoolPickerActivity extends AppCompatActivity implements
        ExpandableListView.OnGroupExpandListener, ProvinceExpandableListAdapter.OnChildTreeViewClickListener {

    public final static String KEY_REQUEST_SCHOOL = "school";
    private Context context = this;
    private ArrayList<ProvinceEntity> provinces;
    private PinnedHeaderExpandableListView headerExpandableListView;
    private PinnedHeaderExpandableAdapter HeaderExpandableListViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_picker);
        initData();
        initView();
    }

    private void initData() {

        provinces = new ArrayList<ProvinceEntity>();

        for (int i = 0; i < 10; i++) {
            ProvinceEntity provinceEntity = new ProvinceEntity();
            provinceEntity.setProvince_name("广东" + i + "省");
            ArrayList<CityEntity> citys = new ArrayList<CityEntity>();
            for (int j = 0; j < 10; j++) {
                CityEntity cityEntity = new CityEntity();
                cityEntity.setCity_name("城市" + j);
                ArrayList<String> schoolList = new ArrayList<String>();
                for (int k = 0; k < 10; k++) {
                    schoolList.add("仲恺农业工程学院" + k);
                }
                cityEntity.setSchool(schoolList);
                citys.add(cityEntity);
            }
            provinceEntity.setCitys(citys);
            provinces.add(provinceEntity);
        }
    }

    private void initView() {

        headerExpandableListView = (PinnedHeaderExpandableListView) findViewById(R.id.explistview);
        headerExpandableListView.setOnGroupExpandListener(this);
        headerExpandableListView.setHeaderView(getLayoutInflater()
                .inflate(R.layout.item_schoolpicker_province, headerExpandableListView, false));
        ProvinceExpandableListAdapter adapter = new ProvinceExpandableListAdapter(context, provinces, headerExpandableListView);
        adapter.setOnChildTreeViewClickListener(this);
        headerExpandableListView.setAdapter(adapter);


    }

    @Override
    public void onClickPosition(int parentPosition, int groupPosition,
                                int childPosition) {
        // do something
        String childName = provinces.get(parentPosition).getCitys()
                .get(groupPosition).getSchool().get(childPosition)
                .toString();
        Intent intent = new Intent();
        intent.putExtra(KEY_REQUEST_SCHOOL, childName);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        for (int i = 0; i < provinces.size(); i++) {
            if (i != groupPosition) {
                headerExpandableListView.collapseGroup(i);
            }
        }
    }
}
