package com.xuhai.telescopes.httpclient.httpResponseHandle;

import android.support.annotation.NonNull;
import android.util.Log;

import com.xuhai.easeui.domain.EaseUser;
import com.xuhai.telescopes.MyApplication;
import com.xuhai.telescopes.MyHelper;
import com.xuhai.telescopes.db.UserDao;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by chudong on 2015/11/19.
 */
public class AsyncFetchContactsFromServer extends BaseJsonHttpResponseHandle {

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        List<String> team = new ArrayList<>() ;
        Map<String, List<String>> teamUsers = new HashMap();

        if (statusCode == 200) {
            try {
                JSONArray allTeam = response.getJSONArray("groups");
                Map<String, EaseUser> allUsers = new HashMap<String, EaseUser>();//所有用户

                for (int i = 0; i < allTeam.length(); i++) {

                    //获得分组名
                    String teamName = allTeam.getJSONObject(i).getString("name");
                    Log.e("FromServer获得分组", teamName);
                    team.add(teamName);

                    //获得这个分组的所有用户User
                    JSONArray users = allTeam.getJSONObject(i).getJSONArray("users");
                    Log.e("这个分组用户", users.toString());
                    List<String> oneLineUsersNameList = new ArrayList<>();//这个分组的所有用户名
                    for (int j = 0; j < users.length(); j++) {
                        JSONObject userjson = users.getJSONObject(j);
                        Log.e("FromServer获得用户", userjson.toString());

                        oneLineUsersNameList.add(userjson.getString("name"));
                        EaseUser user = null;
                        user = setUserFromJson(userjson);
                        allUsers.put(user.getUsername(), user);
                        Log.e("allUser",allUsers.toString());                    }
                    teamUsers.put(teamName, oneLineUsersNameList);
                }

                MyHelper.getInstance().setTeam(team);
                MyHelper.getInstance().setTeamUsers(teamUsers);
                //把所有用户存到内存
                // 存入内存
                MyHelper.getInstance().getContactList().clear();
                MyHelper.getInstance().getContactList().putAll(allUsers);
                // 存入db
                UserDao dao = new UserDao(MyApplication.applicationContext);
                List<EaseUser> users = new ArrayList<EaseUser>(allUsers.values());
                dao.saveContactList(users);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("FromServer", "解析分组信息出错");
            } catch (Exception en) {
                en.printStackTrace();
            }
        }

    }
}
