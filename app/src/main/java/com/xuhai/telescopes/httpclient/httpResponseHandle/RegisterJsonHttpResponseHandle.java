package com.xuhai.telescopes.httpclient.httpResponseHandle;

import android.util.Log;

import com.xuhai.easeui.domain.EaseUser;
import com.xuhai.telescopes.MyHelper;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by chudong on 2015/11/20.
 */
public class RegisterJsonHttpResponseHandle extends BaseJsonHttpResponseHandle {
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        if (statusCode == 200) {
            try {
                EaseUser user = new EaseUser();
                JSONObject userjson = response.getJSONObject("user");
                Log.e("http", userjson.toString());
                user= setUserFromJson(userjson);
                MyHelper.getInstance().setCurrentUserName(user.getUsername());
//                MyHelper.getInstance().setCurrentUserToken(user.getToken());
//                MyHelper.getInstance().setCurrentUser(user);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Regiter", "解析user json数据出错");
            }
        }
    }
}
