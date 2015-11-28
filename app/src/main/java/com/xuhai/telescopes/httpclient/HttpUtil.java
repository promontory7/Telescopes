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

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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


    //===============================分组与好友-----------------------------------------//


    /**
     * 获得某个用户信息
     * @param username
     * @param jsonHttpResponseHandler
     */
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
    public void getGroupsAndUsers(JsonHttpResponseHandler jsonHttpResponseHandler) {
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
    public void getGroupsInfomations(String umeng, int id, JsonHttpResponseHandler jsonHttpResponseHandler) {
        asyncHttpClient.get(addTokenTOUrl(Constant.getGroupsInfomations_url, umeng) + id, jsonHttpResponseHandler);

    }


    /**
     * 创建分组
     * @param group_name
     * @param jsonHttpResponseHandler
     */
    public void createAnGroup(String group_name,JsonHttpResponseHandler jsonHttpResponseHandler){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", group_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpEntity httpEntity = null;
        try {
            httpEntity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.e("createAnGroup", group_name);
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.post(MyApplication.applicationContext, Constant.createAnGroup_url,
                httpEntity, "application/json", jsonHttpResponseHandler);
    }


    /**
     * 设置/修改某个分组信息
     *
     * @param umeng
     * @param name
     * @param jsonHttpResponseHandler
     */
    public void modifyGroudsInfomations(String umeng, int id, String name, JsonHttpResponseHandler jsonHttpResponseHandler) {
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


    public void addUsersToGroup(String[] userID, String groupID,JsonHttpResponseHandler jsonHttpResponseHandler){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("users", userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpEntity httpEntity = null;
        try {
            httpEntity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = Constant.addUserToGroup+groupID+"users";
        Log.e("addUserToGroup", userID.toString());
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.post(MyApplication.applicationContext, url,
                httpEntity, "application/json", jsonHttpResponseHandler);
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


    public void acceptOrRejectFriendship(String friendship, String acceptOrRrject, JsonHttpResponseHandler jsonHttpResponseHandler) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("accept", acceptOrRrject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpEntity httpEntity = null;
        try {
            httpEntity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = Constant.addFriends_url + "/" + friendship + "/confirmed";

        Log.e("同意/拒绝好友申请", url);
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.post(MyApplication.applicationContext, url,
                httpEntity, "application/json", jsonHttpResponseHandler);
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


    /**
     * 加入黑名单
     * @param friendID
     * @param jsonHttpResponseHandler
     */
    public void moveInBlacklist(String[] friendID,JsonHttpResponseHandler jsonHttpResponseHandler) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_ids", friendID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpEntity httpEntity = null;
        try {
            httpEntity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("moveToBlacklist", friendID.toString());
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.post(MyApplication.applicationContext, Constant.moveToBlacklist_url,
                httpEntity, "application/json", jsonHttpResponseHandler);
    }


    /**
     * 移除黑名单
     * @param friendID
     * @param jsonHttpResponseHandler
     */
    public void moveOutFromBlacklist(String[] friendID,JsonHttpResponseHandler jsonHttpResponseHandler){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_ids", friendID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpEntity httpEntity = null;
        try {
            httpEntity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("movOutFromBlacklist", friendID.toString());
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.delete(MyApplication.applicationContext, Constant.moveToBlacklist_url,
                httpEntity, "application/json", jsonHttpResponseHandler);    }


    /**
     * 获得很名单列表
     * @param jsonHttpResponseHandler
     */
    public void getBlacklist(JsonHttpResponseHandler jsonHttpResponseHandler){
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(Constant.blacklist_url,jsonHttpResponseHandler);
    }


    //------------------------------群组---------------------------------------------//

    /**
     * 获得所有群组
     *
     * @param jsonHttpResponseHandler
     */
    public void getAllAlliesFromServe(JsonHttpResponseHandler jsonHttpResponseHandler) {
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(Constant.getAllAlliesFromServe_url, jsonHttpResponseHandler);

    }

    /**
     * 创建群组
     *
     * @param allyName
     * @param number
     * @param description
     * @param jsonHttpResponseHandler
     */
    public void createAnAlly(String allyName, int number, String description, JsonHttpResponseHandler jsonHttpResponseHandler) {
        JSONObject ally = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        HttpEntity httpEntity = null;
        try {
            ally.put("name", allyName);
            ally.put("size", number);
            ally.put("description", description);
            jsonObject.put("ally", ally);
        } catch (JSONException e) {
            Log.d("createAnAlly", "创建群参数出错");
            e.printStackTrace();
        }

        try {
            httpEntity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.post(MyApplication.applicationContext, Constant.createAnAlly_url, httpEntity, "application/json", jsonHttpResponseHandler);
    }

    /**
     * 获得群组详细信息
     *
     * @param allyID
     * @param jsonHttpResponseHandler
     */
    public void getAnAllyInfomations(String allyID, JsonHttpResponseHandler jsonHttpResponseHandler) {
        String url = Constant.getAnAllyInfomations_url + "/" + allyID;
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(url, jsonHttpResponseHandler);
    }


    /**
     * 修改群组信息
     *
     * @param allyID
     * @param allyName
     * @param number
     * @param description
     * @param jsonHttpResponseHandler
     */
    public void modifyAnAllyInfomations(String allyID, String allyName, String number, String description, JsonHttpResponseHandler jsonHttpResponseHandler) {
        JSONObject ally = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        HttpEntity httpEntity = null;
        try {
            ally.put("name", allyName);
            ally.put("size", number);
            ally.put("description", description);
            jsonObject.put("ally", ally);
        } catch (JSONException e) {
            Log.d("createAnAlly", "创建群参数出错");
            e.printStackTrace();
        }

        try {
            httpEntity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("login", jsonObject.toString());
        String url = Constant.modifyAnAllyInfomations_url + "/" + allyID;
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.put(MyApplication.applicationContext, url, httpEntity, "application/json", jsonHttpResponseHandler);
    }


    /**
     * 邀请加群
     *
     * @param allyID
     * @param invited
     * @param jsonHttpResponseHandler
     */
    public void inviteToAlly(String allyID, List<String> invited, JsonHttpResponseHandler jsonHttpResponseHandler) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray(invited);
        try {
            jsonObject.put("user_ids", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpEntity httpEntity = null;
        try {
            httpEntity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = Constant.inviteToAnAlly_url + "/" + allyID + "/users/inv_join_ally";
        Log.e("邀请好友进群", "  好友id=" + jsonArray+url);
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.post(MyApplication.applicationContext, url, httpEntity, "application/json", jsonHttpResponseHandler);
    }


    /**
     * 确认加群
     *
     * @param allyID
     * @param jsonHttpResponseHandler
     */
    public void confirmToAnAlly(String allyID, JsonHttpResponseHandler jsonHttpResponseHandler) {
        String url = Constant.conFirmToAnAlly_url + "/" + allyID + "/users/current_user/confirmed";
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        Log.e("comfiemToAlly_url",url);
        asyncHttpClient.post(url, jsonHttpResponseHandler);

    }


    /**
     * 退出群组
     *
     * @param allyID
     * @param jsonHttpResponseHandler
     */
    public void quitFromAnAlly(String allyID, JsonHttpResponseHandler jsonHttpResponseHandler) {
        String url = Constant.quitFromAnAlly_url + "/" + allyID + "/users/current_user/out";
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.delete(url, jsonHttpResponseHandler);

    }


    public String addTokenTOUrl(String url, String umeng) {
        return url + "?access_token=" + umeng;
    }

}
