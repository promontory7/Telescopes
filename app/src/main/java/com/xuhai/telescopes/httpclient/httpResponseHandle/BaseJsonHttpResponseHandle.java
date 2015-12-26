package com.xuhai.telescopes.httpclient.httpResponseHandle;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.xuhai.easeui.domain.EaseUser;
import com.xuhai.telescopes.domain.Ally;
import com.xuhai.telescopes.domain.Net;
import com.xuhai.telescopes.domain.Seaman;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
            user.setUserId((userjson.optString("id")));
            user.setUsername(userjson.optString("name"));
            user.setNick(userjson.optString("nickname"));
            user.setRename(userjson.optString("rename"));
            user.setIdcard(userjson.optString("idcard"));
            user.setEmail(userjson.optString("email"));
            user.setSignature(userjson.optString("signature"));
            user.setEffect(userjson.optString("effect"));
            user.setLevel(userjson.optString("level"));
            user.setPearl(userjson.optString("pearl"));
            user.setCountry(userjson.optString("country"));
            user.setProvince(userjson.optString("province"));
            user.setCity(userjson.optString("city"));
            user.setArea(userjson.optString("area"));
            user.setAddr(userjson.optString("addr"));
            user.setSchool(userjson.optString("school"));
            user.setSchool_state(userjson.optString("school_state"));
            user.setAuthentication(userjson.optInt("authentication"));
            user.setAvatar(userjson.optString("head"));
            user.setBirthday(userjson.optString("birthday"));
            user.setMobile(userjson.optString("mobile"));
            user.setGender(userjson.optInt("gender"));
            user.setToken(userjson.optString("token"));
            user.setLast_login_ip(userjson.optString("last_login_ip"));
            user.setRegister_type(userjson.optString("register_type"));
            user.setCreated_at(userjson.optString("created_at"));
            user.setRole_id(userjson.optString("role_id"));
            user.setRole_name(userjson.optString("role_id"));
            user.setRole_description(userjson.optString("role_id"));

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("http", "获取user json数据出错");
        }
        return user;
    }

    public Ally setAllyFromJson(JSONObject allyjson) {
        Ally ally = new Ally();
        try {
            ally.setId(allyjson.optString("id"));
            ally.setName(allyjson.optString("name"));
            ally.setSize(allyjson.optInt("size"));
            ally.setUser_id(allyjson.optString("user_id"));
            ally.setHuanxin_group_id(allyjson.optString("huanxin_group_id"));
            ally.setDescription(allyjson.optString("description"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ally;
    }

    public Net setNetFromJson(JSONObject netjson) {
        Net net = new Net();
        try {
            net.setId(netjson.optString("id"));
            net.setTask(netjson.optString("task"));
            net.setStatus(netjson.optString("status"));
            net.setTotal_count(netjson.optString("total_count"));
            net.setTime(netjson.optString("time"));
            net.setUsername(netjson.optString("username"));
            net.setSeaman_role(netjson.optString("seaman_role"));
            net.setSummary(netjson.optString("summary"));

            JSONArray seamenlist = netjson.getJSONArray("seamen");
            List<Seaman> seamen= new ArrayList<Seaman>();

            Seaman seaman = new Seaman();
            JSONObject seamenjson = new JSONObject();
            for(int i =0;i<seamenlist.length();i++){
                seamenjson = seamenlist.getJSONObject(i);
                seaman.setId(seamenjson.optString("id"));
                seaman.setSeaman_role_id(seamenjson.optString("seaman_role_id"));
                seaman.setSeaman_role_name(seamenjson.optString("seaman_role_name"));
                seamen.add(seaman);
            }

            net.setSeamen(seamen);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return net;
    }
}
