package com.xuhai.telescopes.httpclient;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.xuhai.telescopes.Constant;
import com.xuhai.telescopes.MyApplication;
import com.xuhai.telescopes.MyHelper;
import com.xuhai.telescopes.httpclient.httpResponseHandle.LogoutJsonHttpResponseHandle;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by chudong on 2015/11/18.
 */
public class HttpUtil {

    private static HttpUtil instance;
    private static AsyncHttpClient asyncHttpClient;
    private static SyncHttpClient syncHttpClient;


    public HttpUtil() {
        asyncHttpClient = new AsyncHttpClient();
        syncHttpClient = new SyncHttpClient();
    }

    public static synchronized HttpUtil getInstance() {
        if (instance == null) {
            instance = new HttpUtil();
        }
        return instance;
    }


    /**
     * 用户注册
     *
     * @param username    用户名
     * @param password    密码
     * @param zone        地区 86
     * @param code        验证码
     * @param umeng_token umeng token
     */
    public void register(String username, String password, int zone, int code, String umeng_token, JsonHttpResponseHandler jsonHttpResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        params.put("zone", zone);
        params.put("code", code);
        params.put("umeng", umeng_token);
        Log.d("http", "注册");
        asyncHttpClient.post(Constant.register_url, params, jsonHttpResponseHandler);
    }


    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param umeng
     */
    public void login(String username, String password, String umeng, JsonHttpResponseHandler jsonHttpResponseHandler) {
        JSONObject object = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        HttpEntity httpEntity = null;
        try {
            object.put("username", username);
            object.put("password", password);
            Log.e("device_umeng", umeng);
            object.put("umeng", umeng);
            jsonObject.put("user", object);
        } catch (JSONException e) {
            Log.d("login", "登录参数出错");
            e.printStackTrace();
        }

        try {
            httpEntity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("login", jsonObject.toString());
        asyncHttpClient.post(MyApplication.applicationContext, Constant.login_url, httpEntity, "application/json", jsonHttpResponseHandler);
    }


    /**
     * 注销登陆
     *
     * @param jsonHttpResponseHandler
     */
    public void logout(JsonHttpResponseHandler jsonHttpResponseHandler) {
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        Log.e("logout", "注销登陆");
        asyncHttpClient.delete(Constant.logout_url, jsonHttpResponseHandler);
    }


    public void getOneUserInformation(String username, JsonHttpResponseHandler jsonHttpResponseHandler) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpEntity httpEntity = null;
        try {
            httpEntity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(MyApplication.applicationContext, Constant.getOneUserInfomations_url, httpEntity, "application/json", jsonHttpResponseHandler);

    }


    /**
     * 修改用户信息
     *
     * @param umeng
     * @param entity
     */
    public void modifyUserInfomations(Context context, String umeng, HttpEntity entity, JsonHttpResponseHandler jsonHttpResponseHandler) {
        asyncHttpClient.put(context, addTokenTOUrl(Constant.modifyUsersInfomations_url, umeng), entity, "application/json", jsonHttpResponseHandler);
    }


    /**
     * 获得分组列表及用户列表
     *
     * @param jsonHttpResponseHandler
     */
    public void getGroudsAndUsers(JsonHttpResponseHandler jsonHttpResponseHandler) {
        syncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        Log.e("httpGetGroupAndUser请求", MyHelper.getInstance().getCurrentUserToken());

        syncHttpClient.get(Constant.getGroupsAndUsers_url, jsonHttpResponseHandler);

    }


    /**
     * 获得某个分组信息
     *
     * @param umeng
     * @param jsonHttpResponseHandler
     */
    public void getGroudsInfomations(String umeng, int id, JsonHttpResponseHandler jsonHttpResponseHandler) {
        asyncHttpClient.get(addTokenTOUrl(Constant.getGroupsInfomations_url, umeng) + id, jsonHttpResponseHandler);

    }


    /**
     * 设置/修改某个分组信息
     *
     * @param umeng
     * @param name
     * @param jsonHttpResponseHandler
     */
    public void setGroudsInfomations(String umeng, int id, String name, JsonHttpResponseHandler jsonHttpResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("name", name);
        asyncHttpClient.put(addTokenTOUrl(Constant.setGroupsInfomations_url, umeng) + id, params, jsonHttpResponseHandler);

    }


    /**
     * 删除分组
     *
     * @param umeng
     * @param id
     * @param jsonHttpResponseHandler
     */
    public void delectGroud(String umeng, int id, JsonHttpResponseHandler jsonHttpResponseHandler) {
        asyncHttpClient.delete(addTokenTOUrl(Constant.deleteGroups_url, umeng) + id, jsonHttpResponseHandler);
    }


    /**
     * 添加好友请求
     *
     * @param friendId
     * @param jsonHttpResponseHandler
     */
    public void addFriends(int friendId, JsonHttpResponseHandler jsonHttpResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("friend_id", friendId);
        Log.e("addFriend添加好友", "  好友id=" + friendId);
        asyncHttpClient.removeAllHeaders();
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.post(Constant.addFriends_url, params, jsonHttpResponseHandler);

    }


    /**
     * 删除好友
     *
     * @param umeng
     * @param friendId
     * @param jsonHttpResponseHandler
     */
    public void delectFriend(String umeng, int friendId, JsonHttpResponseHandler jsonHttpResponseHandler) {
        asyncHttpClient.delete(addTokenTOUrl(Constant.deleteFriends_url, umeng) + friendId, jsonHttpResponseHandler);
    }


    public void accessFriendApplicaton() {

    }


    public String addTokenTOUrl(String url, String umeng) {
        return url + "?access_token=" + umeng;
    }


}
