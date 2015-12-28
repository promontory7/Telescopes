package com.xuhai.telescopes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.LocationListRecyclerViewAdapter;
import com.xuhai.telescopes.domain.Location;

import java.util.ArrayList;

import carbon.widget.Button;

/**
 * Created by chudong on 2015/12/5.
 */
public class AddLocationActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String KEY_REQUEST_LOCATION = "locations";

    ArrayList<Location> locations = new ArrayList<Location>();
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
        locations.add(new Location("1", "广东", "广州"));
        locations.add(new Location("2", "广东", "广州"));
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.pick_location);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_gf_back);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.confirm:
                        Intent intent = new Intent();
                        intent.putParcelableArrayListExtra(KEY_REQUEST_LOCATION,  locations);
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                }
                return true;
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.location_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new LocationListRecyclerViewAdapter(this, locations));

        Button btn_school = (Button) findViewById(R.id.btn_school);
        Button ben_city = (Button) findViewById(R.id.btn_city);
        btn_school.setOnClickListener(this);
        ben_city.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Location location_data = null;
        if (requestCode == REQUEST_CITY && resultCode == RESULT_OK) {
            location_data = data.getParcelableExtra(CityPickerActivity.KEY_REQUEST_CITY);
        } else if (requestCode == REQUEST_SCHOOL && resultCode == RESULT_OK) {
            location_data = data.getParcelableExtra(SchoolPickerActivity.KEY_REQUEST_SCHOOL);
        } else {
        }
        locations.add(location_data);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.confirm, menu);
        return true;
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
