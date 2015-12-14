package com.xuhai.telescopes.model;

import com.xuhai.telescopes.db.UserDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 */
public class OceanDetailModel extends BaseModel{
    public int id;
    public String summary;
    public String content;
    public String created_at;
    public String updated_at;
    public int status;//1-在发表，2-已结题
    public ArrayList<String> imageList;
    public int c_size;
    public OceanUserModel user;
    public OceanDetailModel(){
        super();
    }

    public OceanDetailModel(String msg){
        super(msg);
    }

    @Override
    public void init(JSONObject jSon) throws JSONException {
        JSONObject questionJson = jSon.getJSONObject("question");
        id = questionJson.optInt("id");
        summary = questionJson.optString("summary");
        content = questionJson.optString("content");
        status = questionJson.optInt("status");
        created_at = questionJson.optString("created_at");
        imageList = new ArrayList<String>();
        if (jSon.has("imgs")){
            JSONArray imgArray = jSon.getJSONArray("imgs");
            for (int i = 0;i<imgArray.length();i++){
                JSONObject imgJson = imgArray.getJSONObject(i);
                String url = imgJson.optString("url");
                if(url != null){
                    imageList.add(url);
                }
            }
        }
        c_size = jSon.optInt("c_size");
        if(jSon.has("user")){
            user = new OceanUserModel(jSon.getString("user"));
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

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return modelList;
    }
}
