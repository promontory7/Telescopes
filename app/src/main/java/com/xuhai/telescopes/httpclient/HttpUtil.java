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

import java.io.File;
import java.io.FileNotFoundException;
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
     *
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
     *
     * @param group_name
     * @param jsonHttpResponseHandler
     */
    public void createAnGroup(String group_name, JsonHttpResponseHandler jsonHttpResponseHandler) {
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


    public void addUsersToGroup(String[] userID, String groupID, JsonHttpResponseHandler jsonHttpResponseHandler) {
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
        String url = Constant.addUserToGroup + groupID + "users";
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
     *
     * @param friendID
     * @param jsonHttpResponseHandler
     */
    public void moveInBlacklist(String[] friendID, JsonHttpResponseHandler jsonHttpResponseHandler) {
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
     *
     * @param friendID
     * @param jsonHttpResponseHandler
     */
    public void moveOutFromBlacklist(String[] friendID, JsonHttpResponseHandler jsonHttpResponseHandler) {
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
                httpEntity, "application/json", jsonHttpResponseHandler);
    }


    /**
     * 获得很名单列表
     *
     * @param jsonHttpResponseHandler
     */
    public void getBlacklist(JsonHttpResponseHandler jsonHttpResponseHandler) {
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(Constant.blacklist_url, jsonHttpResponseHandler);
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
        Log.e("邀请好友进群", "  好友id=" + jsonArray + url);
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
        Log.e("comfiemToAlly_url", url);
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


    //-----------------------------------大海---------------------------------


    public String addTokenTOUrl(String url, String umeng) {
        return url + "?access_token=" + umeng;
    }

    /**
     * 获取大海某主题所有评论的用户名单
     *
     * @param context                 上下文
     * @param oceanId                 大海主题id
     * @param jsonHttpResponseHandler
     */
    public void getOceanEffectList(Context context, int oceanId, JsonHttpResponseHandler jsonHttpResponseHandler) {
        String url = Constant.getOceanEffectList.replace(":id", oceanId + "");
        asyncHttpClient.removeAllHeaders();
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(context, url, jsonHttpResponseHandler);
    }

    /**
     * 评选最有影响力的用户
     *
     * @param context                 上下文
     * @param userId                  用户id
     * @param oceanId                 大海id
     * @param jsonHttpResponseHandler
     */
    public void selectMostEffectUser(Context context, int userId, int oceanId, JsonHttpResponseHandler jsonHttpResponseHandler) {
        String url = Constant.selectOceanEffectUser.replace(":id", oceanId + "");
        url = url.replace(":user_id", userId + "");
        asyncHttpClient.removeAllHeaders();
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.post(url, jsonHttpResponseHandler);
    }

    /**
     * 发布大海主题
     *
     * @param context
     * @param content                 发布内容
     * @param jsonHttpResponseHandler
     */
    public void publishOceanTopic(Context context, String content, ArrayList<String> imageList, JsonHttpResponseHandler jsonHttpResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("content", content);
        if (imageList != null) {
            File[] files = new File[imageList.size()];
            for (int i = 0; i < imageList.size(); i++) {
                files[i] = new File(imageList.get(i));
            }
            try {
                params.put("imgs", files);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        asyncHttpClient.removeAllHeaders();
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.post(context, Constant.publishOceanTopic, params, jsonHttpResponseHandler);
    }

    /**
     * 获取大海主题列表
     *
     * @param context
     * @param type
     * @param jsonHttpResponseHandler
     */
    public void getOceanTopicList(Context context, int type, JsonHttpResponseHandler jsonHttpResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("type", type);
        asyncHttpClient.removeAllHeaders();
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(context, Constant.getOceanTopicList, params, jsonHttpResponseHandler);
    }

    /**
     * 获取大海话题详情
     *
     * @param context
     * @param oceanId
     * @param jsonHttpResponseHandler
     */
    public void getOceanTopicDetail(Context context, int oceanId, JsonHttpResponseHandler jsonHttpResponseHandler) {
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(context, Constant.getOceanTopicDetail + oceanId, jsonHttpResponseHandler);
    }

    /**
     * 删除大海主题详情
     *
     * @param context
     * @param oceanId
     * @param jsonHttpResponseHandler
     */
    public void deleteOceanTopicDetail(Context context, int oceanId, JsonHttpResponseHandler jsonHttpResponseHandler) {
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.delete(context, Constant.getOceanTopicDetail + oceanId, jsonHttpResponseHandler);
    }

    public void completeOceanTopicDetail(Context context, String userId, String oceanId, JsonHttpResponseHandler jsonHttpResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("oceanId", oceanId);
        params.put("userId", userId);
        asyncHttpClient.removeAllHeaders();
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(context, Constant.completeOceanTopicDetail, params, jsonHttpResponseHandler);
    }

    /**
     * 邀请用户进入主题评论
     *
     * @param context
     * @param usersId
     * @param oceanId
     * @param jsonHttpResponseHandler
     */
    public void inviteOceanUser(Context context, int[] usersId, int oceanId, JsonHttpResponseHandler jsonHttpResponseHandler) {

        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < usersId.length; i++) {
            arrayList.add(usersId[i]);
        }
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray(arrayList);
        try {
            jsonObject.put("users", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpEntity httpEntity = null;
        try {
            httpEntity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = Constant.inviteOceanUser.replace(":id", oceanId + "");
        Log.e("inviteOceanUser", url);

        Log.e("inviteOceanUser", jsonArray.toString());
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.post(context, url,
                httpEntity, "application/json", jsonHttpResponseHandler);
    }

    /**
     * 获取大海话题评论
     *
     * @param context
     * @param oceanId
     * @param jsonHttpResponseHandler
     */
    public void getOceanTopicComment(Context context, int oceanId, JsonHttpResponseHandler jsonHttpResponseHandler) {
        String url = Constant.getOceanTopicComment.replace(":id", oceanId + "");
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(context, url, jsonHttpResponseHandler);
    }

    /**
     * 获取大海用户评论
     *
     * @param context
     * @param userId
     * @param oceanId
     * @param jsonHttpResponseHandler
     */
    public void getOceanUserComment(Context context, int userId, int oceanId, JsonHttpResponseHandler jsonHttpResponseHandler) {
        String url = Constant.getOceanUserComment.replace(":id", oceanId + "");
        url = url.replace(":user_id", userId + "");
        asyncHttpClient.removeAllHeaders();
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(context, url, jsonHttpResponseHandler);
    }

    /**
     * 获取某用户对某主题的信息链
     *
     * @param context
     * @param userId
     * @param oceanId
     * @param jsonHttpResponseHandler
     */
    public void getOceanLink(Context context, int userId, int oceanId, JsonHttpResponseHandler jsonHttpResponseHandler) {
        String url = Constant.getOceanLink.replace(":id", oceanId + "");
        url = url.replace(":user_id", userId + "");
        asyncHttpClient.removeAllHeaders();
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(context, url, jsonHttpResponseHandler);
    }

    /**
     * 发表大海话题评论
     *
     * @param context
     * @param parentId
     * @param oceanId
     * @param content
     * @param jsonHttpResponseHandler
     */
    public void addOceanTopicComment(Context context, int parentId, int oceanId, String content, JsonHttpResponseHandler jsonHttpResponseHandler) {
        RequestParams params = new RequestParams();
        if (parentId > 0)
            params.put("comment_id", parentId);
        params.put("content", content);
        String url = Constant.getOceanTopicComment.replace(":id", oceanId + "");
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.post(context, url, params, jsonHttpResponseHandler);
    }

    /**
     * 获取用户名片
     *
     * @param context
     * @param userId
     * @param jsonHttpResponseHandler
     */
    public void getUserCard(Context context, String userId, JsonHttpResponseHandler jsonHttpResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(context, Constant.getUserCard, params, jsonHttpResponseHandler);
    }


    //---------------------------------撒网-----------------------------------------

    /**
     * 同步获得网列表
     *
     * @param context
     * @param jsonHttpResponseHandler
     */
    public void getNetsList(Context context, JsonHttpResponseHandler jsonHttpResponseHandler) {
        syncHttpClient.removeAllHeaders();
        syncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        syncHttpClient.get(context, Constant.getNetList, jsonHttpResponseHandler);
    }


    /**
     * 异步获得网列表
     *
     * @param context
     * @param jsonHttpResponseHandler
     */
    public void asyncgetNetsList(Context context, JsonHttpResponseHandler jsonHttpResponseHandler) {
        asyncHttpClient.removeAllHeaders();
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(context, Constant.getNetList, jsonHttpResponseHandler);
    }

    /**
     * 获得某张网详情
     * @param context
     * @param id
     * @param jsonHttpResponseHandler
     */
    public void getOneNet(Context context, String id, JsonHttpResponseHandler jsonHttpResponseHandler) {
        asyncHttpClient.removeAllHeaders();
        String url = Constant.getOneNet.replace(":id",id);
        Log.e("getOneNet", url);
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(context, url, jsonHttpResponseHandler);
    }


    /**
     * 获得某张网的匹配网信息
     * @param context
     * @param id
     * @param jsonHttpResponseHandler
     */
    public void getOneNetReceiver(Context context,String id,JsonHttpResponseHandler jsonHttpResponseHandler){
        asyncHttpClient.removeAllHeaders();
        String url = Constant.getOneNetReceiver.replace(":id",id);
        Log.e("getOneNetReceiver", url);
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(context, url, jsonHttpResponseHandler);
    }

    /**
     * 撒网
     * @param context
     * @param sea_net
     * @param seamen
     * @param location
     * @param jsonHttpResponseHandler
     */
    public void castNet(Context context,String sea_net,String seamen,String location,JsonHttpResponseHandler jsonHttpResponseHandler){
        RequestParams params = new RequestParams();
        params.put("sea_net",sea_net);
        params.put("seamen",seamen);
        params.put("location", location);
        asyncHttpClient.removeAllHeaders();
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.post(Constant.castNet, params, jsonHttpResponseHandler);
    }


    /**
     * 删除某张网
     * @param context
     * @param id
     * @param jsonHttpResponseHandler
     */
    public void delectNet(Context context,String id,JsonHttpResponseHandler jsonHttpResponseHandler){
        asyncHttpClient.removeAllHeaders();
        String url = Constant.deleteNet.replace(":id",id);
        Log.e("delectNet", url);
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.delete(context, url, jsonHttpResponseHandler);
    }


    /**
     * 获得学校列表
     * @param context
     * @param jsonHttpResponseHandler
     */
    public void getSchoolList(Context context,JsonHttpResponseHandler jsonHttpResponseHandler){
        asyncHttpClient.removeAllHeaders();
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(context, Constant.getSchoolList, jsonHttpResponseHandler);
    }

    /**
     * 获得角色列表
     * @param context
     * .
     * @param jsonHttpResponseHandler
     */
    public void getRoleList(Context context,JsonHttpResponseHandler jsonHttpResponseHandler){
        asyncHttpClient.removeAllHeaders();
        asyncHttpClient.addHeader("Authorization", "Token token=" + MyHelper.getInstance().getCurrentUserToken());
        asyncHttpClient.get(context, Constant.getRoleList, jsonHttpResponseHandler);
    }

}
