package com.xuhai.telescopes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.xuhai.telescopes.R;

/**
 * Created by chudong on 2015/12/1.
 */
public class CreateNestActivity extends AppCompatActivity {
    public static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_net);
        initView();
    }

    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton btn_location = (ImageButton) findViewById(R.id.btn_location);
        btn_location.setOnClickListener(myOnClickListener);
    }

    View.OnClickListener myOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_location:
                    Intent intent = new Intent(CreateNestActivity.this, AddLocationActivity.class);
                    startActivityForResult(intent, REQUEST);
                    break;
            }
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CreateNestActivity.REQUEST && resultCode == RESULT_OK) {
            //图标变颜色
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
