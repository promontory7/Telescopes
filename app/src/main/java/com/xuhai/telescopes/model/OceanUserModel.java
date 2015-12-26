package com.xuhai.telescopes.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 */
public class OceanUserModel extends BaseModel{

    public int userid;
    public String name;
    public String nickname;
    public String head;
    public int age;
    public String city;

    public OceanUserModel(){
        super();
    }

    public OceanUserModel(String msg){
        super(msg);
    }

    @Override
    public void init(JSONObject jSon) throws JSONException {
        userid = jSon.optInt("id");
        nickname = jSon.optString("nickname");
        name = jSon.optString("name");
        head = jSon.optString("head");
    }

    public static ArrayList<OceanUserModel> getList(String msg){
        ArrayList<OceanUserModel> modelList = new ArrayList<OceanUserModel>();
        try {

            JSONArray jSonArray = new JSONArray(msg);
            for (int i = 0;i<jSonArray.length();i++){
                OceanUserModel model = new OceanUserModel();
                model.init(jSonArray.getJSONObject(i));
                modelList.add(model);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return modelList;
    }
}
