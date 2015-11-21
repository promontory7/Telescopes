/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xuhai.telescopes.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.umeng.message.UmengRegistrar;
import com.xuhai.easeui.utils.EaseCommonUtils;
import com.xuhai.telescopes.MyApplication;
import com.xuhai.telescopes.MyHelper;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.httpclient.HttpUtil;
import com.xuhai.telescopes.httpclient.httpResponseHandle.LoginJsonHttpResopnseHandle;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * 登陆页面
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    public static final int REQUEST_CODE_SETNICK = 1;
    private EditText usernameEditText;
    private EditText passwordEditText;

    private boolean progressShow;
    private boolean autoLogin = false;

    private String currentUsername;
    private String currentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 如果登录成功过，直接进入主页面
        if (MyHelper.getInstance().isLoggedIn()) {
            autoLogin = true;
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

            return;
        }
        setContentView(R.layout.em_activity_login);

        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);

        // 如果用户名改变，清空密码
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEditText.setText(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (MyHelper.getInstance().getCurrentUsernName() != null) {
            usernameEditText.setText(MyHelper.getInstance().getCurrentUsernName());
        }
    }

    /**
     * 登录
     *
     * @param view
     */
    public void login(View view) {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        currentUsername = usernameEditText.getText().toString().trim();
        currentPassword = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                progressShow = false;
            }
        });
        pd.setMessage(getString(R.string.Is_landing));
        pd.show();

        String device_token = UmengRegistrar.getRegistrationId(this);
        Log.e("device_token", device_token);

        final long start = System.currentTimeMillis();

        //自己后台服务器登录
        HttpUtil.getInstance().login(currentUsername, currentPassword, device_token,
                new LoginJsonHttpResopnseHandle() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        if (statusCode == 200) {

                            // 调用sdk登陆方法登陆聊天服务器
                            EMChatManager.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

                                @Override
                                public void onSuccess() {
                                    if (!progressShow) {
                                        return;
                                    }
                                    // 登陆成功，保存用户名
//                                    MyHelper.getInstance().setCurrentUserName(currentUsername);
                                    // 注册群组和联系人监听
                                    MyHelper.getInstance().registerGroupAndContactListener();

                                    // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                                    // ** manually load all local groups and
                                    EMGroupManager.getInstance().loadAllGroups();
                                    EMChatManager.getInstance().loadAllConversations();

                                    // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
                                    boolean updatenick = EMChatManager.getInstance().updateCurrentUserNick(
                                            MyApplication.currentUserNick.trim());
                                    if (!updatenick) {
                                        Log.e("LoginActivity", "update current user nick fail");
                                    }
                                    //异步获取当前用户的昵称和头像(从自己服务器获取，demo使用的一个第三方服务)
                                    MyHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();

                                    if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
                                        pd.dismiss();
                                    }
                                    // 进入主页面
                                    Intent intent = new Intent(LoginActivity.this,
                                            MainActivity.class);
                                    startActivity(intent);

                                    finish();
                                }

                                @Override
                                public void onProgress(int progress, String status) {
                                }

                                @Override
                                public void onError(final int code, final String message) {
                                    if (!progressShow) {
                                        return;
                                    }
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            pd.dismiss();
                                            Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, final JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);

                        if (!progressShow) {
                            return;
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + errorResponse.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }


    /**
     * 注册
     *
     * @param view
     */
    public void register(View view) {
        startActivityForResult(new Intent(this, RegisterActivity.class), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (autoLogin) {
            return;
        }
    }
}
