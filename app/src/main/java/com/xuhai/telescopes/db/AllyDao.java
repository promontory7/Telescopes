package com.xuhai.telescopes.db;

import android.content.Context;

import com.xuhai.telescopes.domain.Ally;

import java.util.List;

/**
 * Created by chudong on 2015/11/24.
 */
public class AllyDao {
    static final String ALLY_TABLE_NAME = "ally";
    static final String COLUMN_NAME_ID = "id";
    static final String COLUMN_NAME_NAME =  "name";
    static final String COLUMN_NAME_SIZE = "size" ;
    static final String COLUMN_NAME_USERS_ID = "users_id";
    static final String COLUMN_NAME_HUANXIN_GROUP_ID = "huanxin_group_id";
    static final String COLUMN_NAME_DESCRIPTION = "description";

    public AllyDao(Context context){
    }

    public void saveAllies(List<Ally> allies){
        DemoDBManager.getInstance().saveAllies(allies);
    }

    public List<Ally> getAllies(){
        return DemoDBManager.getInstance().getAllies();
    }

}
