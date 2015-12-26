package com.xuhai.telescopes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.LocationListRecyclerViewAdapter;

import java.util.ArrayList;

import carbon.widget.Button;

/**
 * Created by chudong on 2015/12/5.
 */
public class AddLocationActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> location = new ArrayList<String>();
    Context context = this;
    RecyclerView recyclerView;
    public final static int REQUEST_SCHOOL = 2;
    public final static int REQUEST_CITY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initData();
        initView();
    }

    private void initData() {
        location.add("仲恺农业工程学院");
        location.add("仲恺农业工程学院");
        location.add("仲恺农业工程学院");
        location.add("仲恺农业工程学院");
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new LocationListRecyclerViewAdapter(this, location));

        Button btn_school = (Button) findViewById(R.id.btn_school);
        Button ben_city = (Button) findViewById(R.id.btn_city);
        btn_school.setOnClickListener(this);
        ben_city.setOnClickListener(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String location_data = "";
        if (requestCode == REQUEST_CITY && resultCode == RESULT_OK) {
            location_data = data.getStringExtra(CityPickerActivity.KEY_REQUEST_CITY);
        } else if (requestCode == REQUEST_SCHOOL && resultCode == RESULT_OK) {
            location_data = data.getStringExtra(SchoolPickerActivity.KEY_REQUEST_SCHOOL);
        } else {
        }
        location.add(location_data);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_school:
                startActivityForResult(new Intent(this, SchoolPickerActivity.class), REQUEST_SCHOOL);
                break;
            case R.id.btn_city:
                startActivityForResult(new Intent(this, CityPickerActivity.class), REQUEST_CITY);
        }


    }
}
