package com.xuhai.telescopes.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author LHB
 * @date 2015/12/10 0010.
 */
public class OceanSecondCommentModel extends BaseModel{
    public int comment_id;
    public int question_id;
    public String content;
    public int parent_id;
    public int lft;
    public int rgt;
    public String created_at;
    public String updated_at;
    public int user_id;
    public OceanUserModel user;

    public OceanSecondCommentModel(){
        super();
    }

    public OceanSecondCommentModel(String msg){
        super(msg);
    }

    @Override
    public void init(JSONObject jSon) throws JSONException {
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
}
