package com.xuhai.telescopes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.xuhai.telescopes.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chudong on 2016/1/6.
 */
public class NewAndCreateActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.scan)
    LinearLayout scan;
    @Bind(R.id.team)
    LinearLayout team;
    @Bind(R.id.ally)
    LinearLayout ally;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ally.setOnClickListener(this);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ally:
                startActivityForResult(new Intent(this, NewGroupActivity.class), 0);
                break;
        }
    }
}
