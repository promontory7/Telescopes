package com.xuhai.telescopes.httpclient.httpResponseHandle;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.xuhai.easeui.domain.EaseUser;
import com.xuhai.telescopes.domain.Ally;
import com.xuhai.telescopes.domain.MatchingData;
import com.xuhai.telescopes.domain.MatchingNet;
import com.xuhai.telescopes.domain.MatchingRole;
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
            net.setUsername(netjson.optString("user_name"));
            net.setSeaman_role(netjson.optString("seaman_role"));
            net.setSummary(netjson.optString("summary"));

            JSONArray seamenlist = netjson.getJSONArray("seamen");
            ArrayList<Seaman> seamens = new ArrayList<Seaman>();

            JSONObject seamenjson = new JSONObject();

            for (int i = 0; i < seamenlist.length(); i++) {
                Seaman seaman = new Seaman();
                seamenjson = seamenlist.getJSONObject(i);
                seaman.setId(seamenjson.optString("id"));
                seaman.setSeaman_role_id(seamenjson.optString("seaman_role_id"));
                seaman.setSeaman_role_name(seamenjson.optString("seaman_role_name"));
                seamens.add(seaman);

            }
            net.setSeamen(seamens);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return net;
    }

    public MatchingData setMatchingNetFromJson(JSONObject data) {
        MatchingData matchingData = new MatchingData();
        ArrayList<MatchingRole> matchingRoles = new ArrayList<MatchingRole>();
        ArrayList<MatchingNet> matchingNeedMeNets = new ArrayList<MatchingNet>();

        try {
            JSONArray matchingRole_JA = data.getJSONArray("seaman_nets");
            JSONArray matchingNeedMeNet_JA = data.getJSONArray("need_me_nets");

//-------------------------匹配到的角色网-------------------------------------


            JSONObject matchingRole_JO = null;
            for (int i = 0; i < matchingRole_JA.length(); i++) {
                MatchingRole matchingRole = new MatchingRole();
                matchingRole_JO = matchingRole_JA.getJSONObject(i);

                matchingRole.setSeaman_role_id(matchingRole_JO.optString("seaman_role_id"));
                matchingRole.setSeaman_role_name(matchingRole_JO.optString("seaman_role_name"));
                matchingRole.setUnread_count(matchingRole_JO.optString("unread_count"));

                JSONArray matchingNet_JA = matchingRole_JO.getJSONArray("sea_nets");

                ArrayList<MatchingNet> matchingNets = new ArrayList<MatchingNet>();

                JSONObject matchingNet_JO = null;
                for (int j = 0; j < matchingNet_JA.length(); j++) {
                    MatchingNet matchingNet = new MatchingNet();
                    matchingNet_JO = matchingNet_JA.getJSONObject(j);

                    matchingNet.setNet_id(matchingNet_JO.optString("net_id"));
                    matchingNet.setTask(matchingNet_JO.optString("task"));
                    matchingNet.setUser_name(matchingNet_JO.optString("user_name"));
                    matchingNet.setIs_read(matchingNet_JO.optString("is_read"));
                    matchingNet.setIs_friend(matchingNet_JO.optString("is_friend"));

                    matchingNets.add(matchingNet);
                }
                matchingRole.setMatchingNets(matchingNets);

                matchingRoles.add(matchingRole);


//-------------------------匹配到的需要我的网=======================================


                JSONObject matchingNeedMeNet_JO = null;
                for (int l = 0; l < matchingNeedMeNet_JA.length(); l++) {
                    MatchingNet matchingNeedMeNet = new MatchingNet();
                    matchingNeedMeNet_JO = matchingNeedMeNet_JA.getJSONObject(l);

                    matchingNeedMeNet.setSeaman_role_id(matchingNeedMeNet_JO.optString("seaman_role_id)"));
                    matchingNeedMeNet.setNet_id(matchingNeedMeNet_JO.optString("net_id"));
                    matchingNeedMeNet.setTask(matchingNeedMeNet_JO.optString("task"));
                    matchingNeedMeNet.setUser_name(matchingNeedMeNet_JO.optString("user_name"));
                    matchingNeedMeNet.setIs_read(matchingNeedMeNet_JO.optString("is_read"));
                    matchingNeedMeNet.setIs_friend(matchingNeedMeNet_JO.optString("id_friend"));

                    matchingNeedMeNets.add(matchingNeedMeNet);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("setMatchingNetFromJson", "解析网失败");

        }
        matchingData.setMatchingRoles(matchingRoles);
        matchingData.setMatchingNeedMeNets(matchingNeedMeNets);
        Log.e("setMatchingNetFromJson", "解析网成功");

        return matchingData;
    }
}
