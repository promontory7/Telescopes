package com.xuhai.telescopes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xuhai.easeui.ui.EaseBaseFragment;
import com.xuhai.easeui.widget.EaseTitleBar;
import com.xuhai.telescopes.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chudong on 2016/1/4.
 */
public class ContactListFragmentNew extends EaseBaseFragment implements View.OnClickListener {
    @Bind(R.id.title_bar)
    EaseTitleBar titleBar;
    @Bind(R.id.contactlist_new_shell)
    LinearLayout contactlistNewShell;
    @Bind(R.id.contactlist_friend)
    LinearLayout contactlistFriend;
    @Bind(R.id.contactlist_ally)
    LinearLayout contactlistAlly;
    @Bind(R.id.contactlist_address)
    LinearLayout contactlistAddress;
    @Bind(R.id.contactlist_container)
    RelativeLayout contactlistContainer;

    private FragmentTransaction fragmentTransactin;
    private Fragment friendFragment;
    private Fragment allyFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ease_fragment_contactlist, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView() {
        titleBar.setLeftImageResource(R.drawable.icon_mao);
        titleBar.setRightImageResource(R.drawable.em_add);
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewAndCreateActivity.class));
            }
        });

        contactlistNewShell.setOnClickListener(this);
        contactlistAddress.setOnClickListener(this);
        contactlistFriend.setOnClickListener(this);
        contactlistAlly.setOnClickListener(this);

    }

    @Override
    protected void setUpView() {
        friendFragment = new ContactListFragment();
        allyFragment = new AllyFragment();

        fragmentTransactin = getFragmentManager().beginTransaction();
        fragmentTransactin.add(R.id.contactlist_container, friendFragment)
                .add(R.id.contactlist_container, allyFragment).hide(allyFragment)
                .show(friendFragment).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contactlist_new_shell:
                startActivity(new Intent(getActivity(), NewFriendsMsgActivity.class));
                break;
            case R.id.contactlist_friend:
                changeFragment(allyFragment, friendFragment);
                contactlistFriend.setVisibility(View.GONE);
                contactlistAlly.setVisibility(View.VISIBLE);
                break;
            case R.id.contactlist_ally:
                changeFragment(friendFragment, allyFragment);
                contactlistAlly.setVisibility(View.GONE);
                contactlistFriend.setVisibility(View.VISIBLE);
                break;
        }

    }

    private void changeFragment(Fragment from, Fragment to) {
        fragmentTransactin = getFragmentManager().beginTransaction();
        fragmentTransactin.hide(from);
        if (!to.isAdded()) {
            fragmentTransactin.add(R.id.contactlist_container, to);
        }
        fragmentTransactin.show(to).commit();
    }
}
