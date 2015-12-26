
package com.xuhai.telescopes.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xuhai.easeui.adapter.EaseContactAdapter;
import com.xuhai.easeui.domain.EaseUser;
import com.xuhai.easeui.widget.EaseSidebar;
import com.xuhai.telescopes.Constant;
import com.xuhai.telescopes.MyHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PickContactsActivity extends BaseActivity {
    private ListView listView;
    private ProgressDialog progressDialog;

    private PickContactAdapter contactAdapter;
    private List<String> exitingMembers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.xuhai.telescopes.R.layout.em_activity_group_pick_contacts);

        if (exitingMembers == null)
            exitingMembers = new ArrayList<String>();
        // 获取好友列表
        final List<EaseUser> alluserList = new ArrayList<EaseUser>();
        for (EaseUser user : MyHelper.getInstance().getContactList().values()) {
            if (!user.getUsername().equals(Constant.NEW_FRIENDS_USERNAME) & !user.getUsername().equals(Constant.GROUP_USERNAME) & !user.getUsername().equals(Constant.CHAT_ROOM) & !user.getUsername().equals(Constant.CHAT_ROBOT))
                alluserList.add(user);
        }
        // 对list进行排序
        Collections.sort(alluserList, new Comparator<EaseUser>() {

            @Override
            public int compare(EaseUser lhs, EaseUser rhs) {
                if (lhs.getInitialLetter().equals(rhs.getInitialLetter())) {
                    return lhs.getNick().compareTo(rhs.getNick());
                } else {
                    if ("#".equals(lhs.getInitialLetter())) {
                        return 1;
                    } else if ("#".equals(rhs.getInitialLetter())) {
                        return -1;
                    }
                    return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
                }

            }
        });

        listView = (ListView) findViewById(com.xuhai.telescopes.R.id.list);
        contactAdapter = new PickContactAdapter(this, com.xuhai.telescopes.R.layout.em_row_contact_with_checkbox, alluserList);
        listView.setAdapter(contactAdapter);
        ((EaseSidebar) findViewById(com.xuhai.telescopes.R.id.sidebar)).setListView(listView);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox = (CheckBox) view.findViewById(com.xuhai.telescopes.R.id.checkbox);
                checkBox.toggle();

            }
        });
    }


    public void complete(View v) {
        Intent intent = new Intent();
        intent.putExtra("userid", getToBeAddMembers());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 获取要被添加的成员
     *
     * @return
     */
    private int[] getToBeAddMembers() {

        int length = contactAdapter.isCheckedArray.length;
        ArrayList<String> list = new ArrayList<String>();
        int[] members = null;

        for (int i = 0; i < length; i++) {
//			String username = contactAdapter.getItem(i).getUsername();
            int username = Integer.parseInt(contactAdapter.getItem(i).getUserId());
            if (contactAdapter.isCheckedArray[i] && !exitingMembers.contains(username)) {
                list.add(username + "");
            }
            members = new int[list.size()];
            for (int j = 0; j < list.size(); j++) {
                members[j] = Integer.parseInt(list.get(j));
            }
        }

        return members;
    }

    /**
     * adapter
     */
    private class PickContactAdapter extends EaseContactAdapter {

        private boolean[] isCheckedArray;

        public PickContactAdapter(Context context, int resource, List<EaseUser> users) {
            super(context, resource, users);
            isCheckedArray = new boolean[users.size()];
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
//			if (position > 0) {
            final String username = getItem(position).getUsername();
            // 选择框checkbox
            final CheckBox checkBox = (CheckBox) view.findViewById(com.xuhai.telescopes.R.id.checkbox);
            ImageView avatarView = (ImageView) view.findViewById(com.xuhai.telescopes.R.id.avatar);
            TextView nameView = (TextView) view.findViewById(com.xuhai.telescopes.R.id.name);

            if (checkBox != null) {
                if (exitingMembers != null && exitingMembers.contains(username)) {
                    checkBox.setButtonDrawable(com.xuhai.telescopes.R.drawable.em_checkbox_bg_gray_selector);
                } else {
                    checkBox.setButtonDrawable(com.xuhai.telescopes.R.drawable.em_checkbox_bg_selector);
                }
                // checkBox.setOnCheckedChangeListener(null);

                checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // 群组中原来的成员一直设为选中状态
                        if (exitingMembers.contains(username)) {
                            isChecked = true;
                            checkBox.setChecked(true);
                        }
                        isCheckedArray[position] = isChecked;
                    }
                });
                // 群组中原来的成员一直设为选中状态
                if (exitingMembers.contains(username)) {
                    checkBox.setChecked(true);
                    isCheckedArray[position] = true;
                } else {
                    checkBox.setChecked(isCheckedArray[position]);
                }
            }
//			}
            return view;
        }
    }

    public void back(View view) {
        finish();
    }

}
