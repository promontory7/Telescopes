
package com.xuhai.telescopes;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyApplication extends Application {

    public static Context applicationContext;
    private static MyApplication instance;
    // login user name
    public final String PREF_USERNAME = "username";

    /**
     * 当前用户nickname,为了苹果推送不是userid而是昵称
     */
    public static String currentUserNick = "";

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;
        MyHelper.getInstance().init(applicationContext);
        initUmeng();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public void initUmeng() {
        PushAgent mPushAgent = PushAgent.getInstance(applicationContext);
        mPushAgent.enable();
        PushAgent.getInstance(this).onAppStart();

        mPushAgent.setMessageHandler(new UmengMessageHandler() {
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                new Handler(getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Log.e("msg.title", msg.title);
                        Log.e("msg.custom", msg.custom);
                        Log.e("msg.text", msg.text);

                        if (msg.title.trim().equals("好友申请")) {
                            JSONObject jsonject = null;
                            String user_id = null;
                            String friendship = null;
                            String reason = "加个好友吧";
                            try {
                                jsonject = new JSONObject(msg.custom.toString());
                                user_id = jsonject.optString("user_id");
                                friendship = jsonject.optString("friendship");
                                Log.e("好友添加邀请", "user_id" + user_id + "    friendship" + friendship);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            MyHelper.getInstance().onContactInvited(user_id, friendship, reason);
                        } else if (msg.title.trim().equals("好友申请回馈")) {
                            JSONObject jsonject = null;
                            String user_id = null;
                            try {
                                jsonject = new JSONObject(msg.custom.toString());
                                user_id = jsonject.optString("user_id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            MyHelper.getInstance().onContactAgree(user_id);
                        } else if (msg.title.trim().equals("群消息提醒")) {
                            try {
                                JSONObject jsonObject = new JSONObject(msg.custom.toString());
                                String groupid = jsonObject.optString("ally_id");

                                String text = msg.text.trim();
                                int spilt = text.indexOf("邀请您加入");
                                Log.e("spilt",spilt+"");
                                String inviter = text.substring(0,spilt);
                                String groupname = text.substring(spilt+6);
                                Log.e("groupid",groupid);
                                Log.e("inviter",inviter);
                                Log.e("groupname",groupname);

                                MyHelper.getInstance().onInvitationReceived(groupid,groupname,inviter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception ei){
                                ei.printStackTrace();
                            }

                        } else {
                           Log.e("umeng","WHAT !!!");
                        }
                    }
                });
            }
        });
    }
}
