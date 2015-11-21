
package com.xuhai.telescopes;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

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

                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }

                        Log.e("推送title", msg.title);
                        Log.e("推送custom", msg.custom.toString());
                        Log.e("推送text", msg.text);
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }
}
