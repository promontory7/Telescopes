package com.xuhai.telescopes.model;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admin on 2015/12/8.
 */
public class OceanCommentModel extends BaseModel{

    public int question_id;
    public int comment_id;
    public String content;
    public int parent_id;
    public int lft;
    public int rgt;
    public String created_at;
    public String updated_at;
    public int user_id;
    public OceanUserModel user;
    public ArrayList<OceanSecondCommentModel> secondList ;


    public OceanCommentModel(){
        super();
    }

    public OceanCommentModel(String msg){
        super(msg);
    }

    @Override
    public void init(JSONObject jSon) throws JSONException {
        secondList = new ArrayList<OceanSecondCommentModel>();
        question_id = jSon.optInt("question_id");
        comment_id = jSon.optInt("comment_id");
        content = jSon.optString("content");
        parent_id = jSon.optInt("parent_id");
        lft = jSon.optInt("lft");
        rgt = jSon.optInt("rgt");
        created_at = jSon.optString("created_at");
        updated_at = jSon.optString("updated_at");
        user_id = jSon.optInt("user_id");
        if(jSon.has("user")){
            user = new OceanUserModel(jSon.getString("user"));
        }
    }

    public static ArrayList<OceanCommentModel> getList(String msg){
        ArrayList<OceanCommentModel> modelList = new ArrayList<OceanCommentModel>();
        HashMap<Integer,Integer> tempMap = new HashMap<Integer,Integer>();
        int position = 0;
        try {
            JSONArray jSonArray = new JSONArray(msg);

            for (int i = 0;i<jSonArray.length();i++){
                JSONObject tempJson = jSonArray.getJSONObject(i);
                if (tempJson.optInt("parent_id",-1)<=0){
                    OceanCommentModel model = new OceanCommentModel();
                    model.init(tempJson);
                    modelList.add(model);
                    tempMap.put(model.comment_id,position);
                    position++;
                }

            }
            for(int i = 0;i<jSonArray.length();i++){
                JSONObject tempJson = jSonArray.getJSONObject(i);
                if (tempJson.optInt("parent_id",-1)>0){
                    OceanSecondCommentModel model = new OceanSecondCommentModel();
                    model.init(tempJson);
                    int tempPosition = tempMap.get(model.parent_id);
                    modelList.get(tempPosition).secondList.add(model);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return modelList;
    }

    public static ArrayList<OceanCommentModel> getListNoLevel(String msg){
        ArrayList<OceanCommentModel> modelList = new ArrayList<OceanCommentModel>();
        try {
            JSONArray jSonArray = new JSONArray(msg);
            for (int i = 0;i<jSonArray.length();i++){
                JSONObject tempJson = jSonArray.getJSONObject(i);
                    OceanCommentModel model = new OceanCommentModel();
                    model.init(tempJson);
                    modelList.add(model);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return modelList;
    }
}
