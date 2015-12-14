package com.xuhai.telescopes.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 2015/12/7.
 */
public class OceanListModel extends BaseModel{
    public int id;
    public String summary;
    public int userid;
    public ArrayList<String> imageList;

    public OceanListModel(){
        super();
    }

    public OceanListModel(String msg){
        super(msg);
    }

    @Override
    public void init(JSONObject jSon) throws JSONException {
        JSONObject questionJson = jSon.getJSONObject("question");
        id = questionJson.optInt("id");
        summary = questionJson.optString("summary");
        userid = questionJson.optInt("user_id");
        if (jSon.has("imgs")){
            JSONArray imgJson = jSon.getJSONArray("imgs");
            for (int i = 0;i<imgJson.length();i++){
                imageList.add(imgJson.getString(i));
            }
        }
    }

    public static ArrayList<OceanListModel> getList(String msg){
        ArrayList<OceanListModel> modelList = new ArrayList<OceanListModel>();
        try {

            JSONArray jSonArray = new JSONArray(msg);
            for (int i = 0;i<jSonArray.length();i++){
                OceanListModel model = new OceanListModel();
                model.init(jSonArray.getJSONObject(i));
                modelList.add(model);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return modelList;
    }
}
