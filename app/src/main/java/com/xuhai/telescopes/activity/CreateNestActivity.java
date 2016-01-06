package com.xuhai.telescopes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xuhai.telescopes.MyHelper;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.CastNetRoleAdapter;
import com.xuhai.telescopes.domain.Location;
import com.xuhai.telescopes.domain.MatchingData;
import com.xuhai.telescopes.domain.Seaman;
import com.xuhai.telescopes.httpclient.HttpUtil;
import com.xuhai.telescopes.httpclient.httpResponseHandle.BaseJsonHttpResponseHandle;
import com.xuhai.telescopes.utils.ToastUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import carbon.widget.Button;

/**
 * Created by chudong on 2015/12/1.
 */
public class CreateNestActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST = 1;
    public static int number = 0;
    private RecyclerView recyclerView;
    private CastNetRoleAdapter castNetRoleAdapter;
    private Context context = this;
    private LinearLayout add_role_layout;

    EditText task_et;
    EditText role_name_et;
    EditText summary_et;

    private String task;
    private String role_name;
    private String summary;
    private ArrayList<Seaman> seamen = new ArrayList<Seaman>();
    private ArrayList<Location> locations = new ArrayList<Location>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_net);
        initView();
    }

    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        seamen.add(new Seaman());
        recyclerView = (RecyclerView) findViewById(R.id.cast_net_recyclerview);
        recyclerView.setLayoutManager(new MyLayoutManager(context));
        castNetRoleAdapter = new CastNetRoleAdapter(this, seamen);
        recyclerView.setAdapter(castNetRoleAdapter);
        Log.e("CreateNestActivity", "setAdapter");
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        add_role_layout = (LinearLayout) findViewById(R.id.cast_net_add_role);
        add_role_layout.setOnClickListener(this);

        ImageButton btn_location = (ImageButton) findViewById(R.id.btn_location);
        btn_location.setOnClickListener(this);

        Button bt_casr_net = (Button) findViewById(R.id.doing_cast_net);
        bt_casr_net.setOnClickListener(this);

        task_et = (EditText) findViewById(R.id.cast_net_task);
        role_name_et = (EditText) findViewById(R.id.cast_net_role_name);
        summary_et = (EditText) findViewById(R.id.cast_net_summary);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CreateNestActivity.REQUEST && resultCode == RESULT_OK) {
            locations =  data.getParcelableArrayListExtra(AddLocationActivity.KEY_REQUEST_LOCATION);
            Log.e("locations",locations.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_location:
                Intent intent = new Intent(CreateNestActivity.this, AddLocationActivity.class);
                startActivityForResult(intent, REQUEST);
                break;
            case R.id.cast_net_add_role:
                seamen.add(new Seaman());
                castNetRoleAdapter.notifyItemInserted(seamen.size() - 1);
                break;
            case R.id.doing_cast_net:

                task=task_et.getText().toString();
                role_name = role_name_et.getText().toString();
                summary = summary_et.getText().toString();

                //测试位置
                locations.clear();
                locations.add(new Location("0","广东","广州"));

                HttpUtil.getInstance().castNet(context,task,role_name,summary,seamen,locations,new BaseJsonHttpResponseHandle(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                            try {
                                JSONObject data = response.getJSONObject("data");
                                MatchingData matchingData =setMatchingNetFromJson(data);
                                Log.e("castNet","撒网成功");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "撒网成功", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });

        }
    }


    private class MyLayoutManager extends LinearLayoutManager {
        public MyLayoutManager(Context context) {
            super(context);
        }

        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {

            Log.e("size", seamen.size() + "");
            if (seamen.size() > 0) {
                View view = recycler.getViewForPosition(0);
                if (view != null) {
                    measureChild(view, widthSpec, heightSpec);
                    int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                    int measureHeight = view.getMeasuredHeight() * seamen.size();
                    setMeasuredDimension(measuredWidth, measureHeight);
                }
            } else {
                super.onMeasure(recycler, state, widthSpec, heightSpec);
            }
        }
    }
}
