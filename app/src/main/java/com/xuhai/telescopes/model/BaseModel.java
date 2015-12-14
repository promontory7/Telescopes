package com.xuhai.telescopes.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2015/12/7.
 */
public abstract class BaseModel {
    public BaseModel(){

    }

    public BaseModel(String msg){

        try {
            JSONObject jsonObject = new JSONObject(msg);
            init(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public abstract void init(JSONObject jSon)throws JSONException ;

}
