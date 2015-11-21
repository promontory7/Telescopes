package com.xuhai.telescopes.httpclient.httpResponseHandle;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.xuhai.easeui.domain.EaseUser;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by chudong on 2015/11/19.
 */
public class BaseJsonHttpResponseHandle extends JsonHttpResponseHandler {

    public BaseJsonHttpResponseHandle() {
        super();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        Log.e("BaseHttpResponse", "statusCode=" + statusCode + "  " + errorResponse.toString());
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        Log.e("BaseHttpResponse", "statusCode=" + statusCode + "  " + errorResponse.toString());

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
        Log.e("BaseHttpResponse", "statusCode=" + statusCode + "  " + responseString);

    }

    public EaseUser setUserFromJson(JSONObject userjson) {
        EaseUser user = new EaseUser();
        try {
            user.setUserId(String.valueOf(userjson.getInt("id")));
            user.setUsername(userjson.getString("name"));
            user.setNick(userjson.getString("nickname"));
            user.setRename(userjson.getString("rename"));
            user.setIdcard(userjson.getString("idcard"));
            user.setEmail(userjson.getString("email"));
            user.setSignature(userjson.getString("signature"));
            user.setEffect(userjson.getString("effect"));
            user.setLevel(userjson.getString("level"));
            user.setPearl(userjson.getString("pearl"));
            user.setCountry(userjson.getString("country"));
            user.setProvince(userjson.getString("province"));
            user.setCity(userjson.getString("city"));
            user.setArea(userjson.getString("area"));
            user.setAddr(userjson.getString("addr"));
            user.setSchool(userjson.getString("school"));
            user.setSchool_state(userjson.getString("school_state"));
            user.setAuthentication(userjson.getInt("authentication"));
            user.setAvatar(userjson.getString("head"));
            user.setBirthday(userjson.getString("birthday"));
            user.setMobile(userjson.getString("mobile"));
            user.setGender(0);
            user.setToken(userjson.getString("token"));
            user.setLast_login_ip(userjson.getString("last_login_ip"));
            user.setRegister_type(userjson.getString("register_type"));
            user.setCreated_at(userjson.getString("created_at"));
            user.setRole_id(userjson.getString("role_id"));
            user.setRole_name("暂无");
            user.setRole_description("暂无");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("http", "获取user json数据出错");
        }
        return user;
    }
}
