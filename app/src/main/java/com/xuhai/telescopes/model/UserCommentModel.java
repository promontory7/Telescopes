package com.xuhai.telescopes.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 */
public class UserCommentModel extends BaseModel{

    public int userid;
    public String name;
    public String nickname;
    public String head;
    public int age;
    public String city;

    public UserCommentModel(){
        super();
    }

    public UserCommentModel(String msg){
        super(msg);
    }

    @Override
    public void init(JSONObject jSon) throws JSONException {
        userid = jSon.optInt("id");
        nickname = jSon.optString("nickname");
        head = jSon.optString("head");
    }

    public static ArrayList<UserCommentModel> getList(String msg){
        ArrayList<UserCommentModel> modelList = new ArrayList<UserCommentModel>();
        try {

            JSONArray jSonArray = new JSONArray(msg);
            for (int i = 0;i<jSonArray.length();i++){
                UserCommentModel model = new UserCommentModel();
                model.init(jSonArray.getJSONObject(i));
                modelList.add(model);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return modelList;
    }
}
