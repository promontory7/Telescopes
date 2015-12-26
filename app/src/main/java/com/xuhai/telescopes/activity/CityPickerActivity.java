package com.xuhai.telescopes.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.PinnedHeaderExpandableAdapter;
import com.xuhai.telescopes.db.DBManager;
import com.xuhai.telescopes.widget.PinnedHeaderExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by chudong on 2015/12/3.
 */
public class CityPickerActivity extends AppCompatActivity {

    public static final String KEY_REQUEST_CITY = "city";
    private ArrayList<String> provinceList = new ArrayList();
    private HashMap<String, List<String>> cityList = new HashMap<String, List<String>>();
    private int expandFlag = -1;//控制列表的展开
    PinnedHeaderExpandableListView explistView;
    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citypicker);
        initView();
        initDate();


    }

    private void initDate() {

        DBManager dbManager = new DBManager();
        SQLiteDatabase sqLiteDatabase = dbManager.openDatabase(context);

        Cursor cursor = sqLiteDatabase.rawQuery("select * from china_provinces_code", null);
        String province = "";
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            province = cursor.getString(cursor.getColumnIndex("name"));
            provinceList.add(province);

            Cursor cursor1 = sqLiteDatabase.rawQuery("select distinct city from china_city_code where province=?", new String[]{province});

            ArrayList<String> cities = new ArrayList<String>();
            for (cursor1.moveToFirst(); !cursor1.isAfterLast(); cursor1.moveToNext()) {
                cities.add(cursor1.getString(cursor1.getColumnIndex("city")));
            }
            cursor1.close();
            cityList.put(province, cities);
        }
        cursor.close();

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        explistView = (PinnedHeaderExpandableListView) findViewById(R.id.explistview);
        explistView.setHeaderView(getLayoutInflater().inflate(R.layout.item_citypicker_header, explistView, false));
        explistView.setAdapter(new PinnedHeaderExpandableAdapter(provinceList, cityList, this, explistView));
        //设置单个分组展开
        explistView.setOnGroupClickListener(new GroupClickListener());

        explistView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String key = provinceList.get(groupPosition);
                String city = cityList.get(key).get(childPosition);
                Intent intent = new Intent();
                cityList.get(key).get(childPosition);
                intent.putExtra(KEY_REQUEST_CITY, city);
                setResult(RESULT_OK, intent);
                finish();
                return false;
            }
        });
    }

    class GroupClickListener implements OnGroupClickListener {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v,
                                    int groupPosition, long id) {
            if (expandFlag == -1) {
                // 展开被选的group
                explistView.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                explistView.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            } else if (expandFlag == groupPosition) {
                explistView.collapseGroup(expandFlag);
                expandFlag = -1;
            } else {
                explistView.collapseGroup(expandFlag);
                // 展开被选的group
                explistView.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                explistView.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            }
            return true;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
