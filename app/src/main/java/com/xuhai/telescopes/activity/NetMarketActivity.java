package com.xuhai.telescopes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.xuhai.telescopes.MyHelper;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.NetMarketAdapter;
import com.xuhai.telescopes.domain.MatchingData;
import com.xuhai.telescopes.domain.MatchingRole;

import java.util.ArrayList;

/**
 * Created by chudong on 2015/12/29.
 */
public class NetMarketActivity extends AppCompatActivity {

    private ExpandableListView expandListView;
    private NetMarketAdapter netMarketAdapter;
    private Context context;

    private ArrayList<MatchingRole> matchingRoles = new ArrayList<MatchingRole>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context =this;
        setContentView(R.layout.activity_net_market);

        initData();
        initView();

        Log.e("matchingRoles", matchingRoles.toString());
        expandListView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        netMarketAdapter = new NetMarketAdapter(this, matchingRoles);
        expandListView.setAdapter(netMarketAdapter);
        expandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent =new Intent(context,NetDetailActivity.class);
                intent.putExtra("net_id", matchingRoles.get(groupPosition).getMatchingNets().get(childPosition).getNet_id());
                startActivity(intent);
                return true;
            }
        });
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("对上的网");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_gf_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        expandListView = (ExpandableListView) findViewById(R.id.net_market_expandableListview);
        expandListView.setGroupIndicator(null);
    }

    private void initData() {
        String net_id = getIntent().getStringExtra("net_id");
        MatchingData matchingData = MyHelper.getInstance().getNetMarket(net_id);

        matchingRoles.addAll(matchingData.getMatchingRoles());

        if(matchingData.getMatchingNeedMeNets()!=null){
            MatchingRole otherMatchingRole = new MatchingRole();
            otherMatchingRole.setSeaman_role_name("需要我角色的撒网者");
            otherMatchingRole.setMatchingNets(matchingData.getMatchingNeedMeNets());
            matchingRoles.add(otherMatchingRole);
        }

    }
}
