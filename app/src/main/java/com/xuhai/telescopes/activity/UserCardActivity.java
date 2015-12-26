package com.xuhai.telescopes.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xuhai.easeui.domain.EaseUser;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.httpclient.HttpUtil;
import com.xuhai.telescopes.httpclient.httpResponseHandle.BaseJsonHttpResponseHandle;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 用户名片
 *
 * @author LHB
 * @date 2015/11/12 0012.
 */
public class UserCardActivity extends BaseActivity implements View.OnClickListener {

    private ImageView leftImage;
    private TextView titleText;
    private TextView rightText;

    private TextView card_name;
    private TextView card_certification;
    private ImageView card_sex;
    private TextView card_school;
    private TextView card_influence;
    private TextView card_grade;
    private TextView card_signature;
    private ImageView card_avater;

    private TextView card_add_to_friend;
    private TextView card_talk;

    private String username;
    private String userId;
    private EaseUser easeUser;
    private ProgressDialog progressDialog;



    public void onCreate(Bundle onsavedInstanceState) {
        super.onCreate(onsavedInstanceState);
        setContentView(R.layout.activity_user_card);
        findViews();
        setContent();
        setListener();
    }

    public void findViews() {
        leftImage = (ImageView) findViewById(R.id.iv_left);
        titleText = (TextView) findViewById(R.id.tv_title);

        card_name = (TextView) findViewById(R.id.card_name);
        card_certification = (TextView) findViewById(R.id.card_certification);
        card_sex = (ImageView) findViewById(R.id.card_sex);
        card_school = (TextView) findViewById(R.id.card_school);
        card_influence = (TextView) findViewById(R.id.influence);
        card_grade = (TextView) findViewById(R.id.card_grade);
        card_signature = (TextView) findViewById(R.id.signature);
        card_avater = (ImageView) findViewById(R.id.card_avater);

        card_add_to_friend = (TextView) findViewById(R.id.card_add_to_friend);
        card_talk = (TextView) findViewById(R.id.card_talk);
    }

    public void setContent() {
        username = getIntent().getStringExtra("username");

        HttpUtil.getInstance().getOneUserInformation(username, new BaseJsonHttpResponseHandle() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (statusCode == 200) {
                    try {
                        JSONObject userjson = response.getJSONObject("user");
                        if (userjson == null) {
                            return;
                        }
                        easeUser = setUserFromJson(userjson);

                        Log.e("findUser,easeUser", easeUser.toString());

                        userId=easeUser.getUserId();
                        card_name.setText(easeUser.getUsername());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(final int statusCode, Header[] headers, Throwable throwable, final JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

//        Drawable drawable = this.getResources().getDrawable(R.drawable.bg_test);
//        headImage.setImageBitmap(ImageUtils.getRoundedCornerBitmap(drawable, 80));

    }

    public void setListener() {
        leftImage.setOnClickListener(this);
        card_add_to_friend.setOnClickListener(this);
        card_talk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.card_add_to_friend:
                progressDialog = new ProgressDialog(this);
                String stri = getResources().getString(R.string.Is_sending_a_request);
                progressDialog.setMessage(stri);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                HttpUtil.getInstance().addFriends(Integer.parseInt(userId), new BaseJsonHttpResponseHandle() {

                    @Override
                    public void onSuccess(final int statusCode, Header[] headers, final JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        if (statusCode == 200) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    progressDialog.dismiss();
                                    String s1 = getResources().getString(R.string.send_successful);
                                    Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                                    Log.e("addFriend", statusCode + response.toString());
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(final int statusCode, Header[] headers, Throwable throwable, final JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                                Toast.makeText(getApplicationContext(), s2, Toast.LENGTH_LONG).show();
                                Log.e("addFriend", statusCode + errorResponse.toString());

                            }
                        });
                    }
                });
                break;
            case R.id.card_talk:
                startActivity(new Intent(this,ChatActivity.class).putExtra("userId",username));
                break;
        }
    }
}
