/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xuhai.telescopes.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.easemob.chat.EMGroupManager;
import com.easemob.exceptions.EaseMobException;
import com.xuhai.easeui.widget.EaseAlertDialog;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.httpclient.HttpUtil;
import com.xuhai.telescopes.httpclient.httpResponseHandle.CreateAnAllyJsonHttpResponseHandle;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class NewGroupActivity extends BaseActivity {
	private EditText groupNameEditText;
	private ProgressDialog progressDialog;
	private EditText introductionEditText;
	private CheckBox checkBox;
	private CheckBox memberCheckbox;
	private LinearLayout openInviteContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.em_activity_new_group);
		groupNameEditText = (EditText) findViewById(R.id.edit_group_name);
		introductionEditText = (EditText) findViewById(R.id.edit_group_introduction);
		checkBox = (CheckBox) findViewById(R.id.cb_public);
		memberCheckbox = (CheckBox) findViewById(R.id.cb_member_inviter);
		openInviteContainer = (LinearLayout) findViewById(R.id.ll_open_invite);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					openInviteContainer.setVisibility(View.INVISIBLE);
				}else{
					openInviteContainer.setVisibility(View.VISIBLE);
				}
			}
		});
	}


	public void save(View v){
		final String groupName = groupNameEditText.getText().toString();
		String description = introductionEditText.getText().toString();
		String st1 = getResources().getString(R.string.Is_to_create_a_group_chat);
		final String st2 = getResources().getString(R.string.Failed_to_create_groups);

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(st1);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();

		if (TextUtils.isEmpty(groupName)) {
			new EaseAlertDialog(this, R.string.Group_name_cannot_be_empty).show();
		} else {
			HttpUtil.getInstance().createAnAlly(groupName, 5, description, new CreateAnAllyJsonHttpResponseHandle() {

				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					String huanxin_group_id = null;
					String groupid = null;
					if (statusCode == 200) {
						try {
							JSONObject allyjson = response.getJSONObject("ally");
							huanxin_group_id = allyjson.optString("huanxin_group_id");
							groupid = allyjson.optString("id");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						runOnUiThread(new Runnable() {
							public void run() {
								progressDialog.dismiss();
								Toast.makeText(NewGroupActivity.this, "创建群成功", Toast.LENGTH_LONG).show();
							}
						});
						startActivityForResult(new Intent(NewGroupActivity.this, GroupPickContactsActivity.class)
								.putExtra("groupName", groupName).putExtra("groupid",groupid).putExtra("huanxin_group_id",huanxin_group_id), 0);
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
					super.onFailure(statusCode, headers, throwable, errorResponse);
					Log.e("createAnAlly", "创建群失败");
					runOnUiThread(new Runnable() {
						public void run() {
							progressDialog.dismiss();
							Toast.makeText(NewGroupActivity.this, st2, Toast.LENGTH_LONG).show();
						}
					});
				}
			});
		}
	}

//	/**
//	 * @param v
//	 */
//	public void save(View v) {
//		String name = groupNameEditText.getText().toString();
//		if (TextUtils.isEmpty(name)) {
//		    new EaseAlertDialog(this, R.string.Group_name_cannot_be_empty).show();
//		} else {
//			// 进通讯录选人
//			startActivityForResult(new Intent(this, GroupPickContactsActivity.class).putExtra("groupName", name), 0);
//		}
//	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String st1 = getResources().getString(R.string.Is_to_create_a_group_chat);
		final String st2 = getResources().getString(R.string.Failed_to_create_groups);
		if (resultCode == RESULT_OK) {
			//新建群组
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage(st1);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.show();

			new Thread(new Runnable() {
				@Override
				public void run() {
					// 调用sdk创建群组方法
					String groupName = groupNameEditText.getText().toString().trim();
					String desc = introductionEditText.getText().toString();
					String[] members = data.getStringArrayExtra("newmembers");
					try {
						if(checkBox.isChecked()){
							//创建公开群，此种方式创建的群，可以自由加入
							//创建公开群，此种方式创建的群，用户需要申请，等群主同意后才能加入此群
						    EMGroupManager.getInstance().createPublicGroup(groupName, desc, members, true,200);
						}else{
							//创建不公开群
						    EMGroupManager.getInstance().createPrivateGroup(groupName, desc, members, memberCheckbox.isChecked(),200);

						}
						runOnUiThread(new Runnable() {
							public void run() {
								progressDialog.dismiss();
								setResult(RESULT_OK);
								finish();
							}
						});
					} catch (final EaseMobException e) {
						runOnUiThread(new Runnable() {
							public void run() {
								progressDialog.dismiss();
								Toast.makeText(NewGroupActivity.this, st2 + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
							}
						});
					}

				}
			}).start();
		}
	}

	public void back(View view) {
		finish();
	}
}
