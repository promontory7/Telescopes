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
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.umeng.message.UmengRegistrar;
import com.xuhai.telescopes.MyApplication;
import com.xuhai.telescopes.MyHelper;
import com.xuhai.telescopes.httpclient.HttpUtil;
import com.xuhai.telescopes.httpclient.httpResponseHandle.RegisterJsonHttpResponseHandle;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * 注册页
 *
 */
public class RegisterActivity extends BaseActivity {
    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText confirmPwdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.xuhai.telescopes.R.layout.em_activity_register);
        userNameEditText = (EditText) findViewById(com.xuhai.telescopes.R.id.username);
        passwordEditText = (EditText) findViewById(com.xuhai.telescopes.R.id.password);
        confirmPwdEditText = (EditText) findViewById(com.xuhai.telescopes.R.id.confirm_password);
    }

    /**
     * 注册
     *
     * @param view
     */
    public void register(View view) {
        final String username = userNameEditText.getText().toString().trim();
        final String pwd = passwordEditText.getText().toString().trim();
        String confirm_pwd = confirmPwdEditText.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, getResources().getString(com.xuhai.telescopes.R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
            userNameEditText.requestFocus();
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, getResources().getString(com.xuhai.telescopes.R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
            passwordEditText.requestFocus();
            return;
        } else if (TextUtils.isEmpty(confirm_pwd)) {
            Toast.makeText(this, getResources().getString(com.xuhai.telescopes.R.string.Confirm_password_cannot_be_empty), Toast.LENGTH_SHORT).show();
            confirmPwdEditText.requestFocus();
            return;
        } else if (!pwd.equals(confirm_pwd)) {
            Toast.makeText(this, getResources().getString(com.xuhai.telescopes.R.string.Two_input_password), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage(getResources().getString(com.xuhai.telescopes.R.string.Is_the_registered));
            pd.show();


            String device_token = UmengRegistrar.getRegistrationId(MyApplication.applicationContext);
            Log.e("device_token", device_token);
            HttpUtil.getInstance().register(username, pwd, 86, 1234, device_token, new RegisterJsonHttpResponseHandle() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (!RegisterActivity.this.isFinishing())
                                pd.dismiss();
                            // 保存用户名
                            MyHelper.getInstance().setCurrentUserName(username);
                            Toast.makeText(getApplicationContext(), getResources().getString(com.xuhai.telescopes.R.string.Registered_successfully), Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, final JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (!RegisterActivity.this.isFinishing())
                                pd.dismiss();

                            Toast.makeText(getApplicationContext(), getResources().getString(com.xuhai.telescopes.R.string.Registration_failed) + errorResponse.toString(), Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            });
        }
    }

    public void back(View view) {
        finish();
    }

}
