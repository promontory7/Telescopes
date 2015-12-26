package com.xuhai.telescopes.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 */
public class OceanModel extends BaseModel{
    public int id;
    public String summary;
    public int userid;
    public int comment_size;
    public int status;//状态值，1-进行中，2-已结题
    public String created_at;
    public ArrayList<String> imageList;

    public OceanModel(){
        super();
    }

    public OceanModel(String msg){
        super(msg);
    }

    @Override
    public void init(JSONObject jSon) throws JSONException {
        JSONObject questionJson = jSon.getJSONObject("question");
        id = questionJson.optInt("id");
        summary = questionJson.optString("summary");
        userid = questionJson.optInt("user_id");
        comment_size = jSon.optInt("comment_size");
        status = jSon.optInt("status");
        created_at = jSon.optString("created_at");
        imageList = new ArrayList<String>();
        Log.e("status",status+"");
        if (jSon.has("imgs")){
            JSONArray imgJson = jSon.getJSONArray("imgs");
            for (int i = 0;i<imgJson.length();i++){
                imageList.add(imgJson.getString(i));
            }
        }
    }

    public static ArrayList<OceanModel> getList(String msg){
        ArrayList<OceanModel> modelList = new ArrayList<OceanModel>();
        try {

            JSONArray jSonArray = new JSONArray(msg);
            for (int i = 0;i<jSonArray.length();i++){
                OceanModel model = new OceanModel();
                model.init(jSonArray.getJSONObject(i));
                modelList.add(model);
                Log.e("大海话题列表",modelList.toArray().toString());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return modelList;
    }
}
