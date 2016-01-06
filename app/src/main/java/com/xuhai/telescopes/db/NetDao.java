package com.xuhai.telescopes.db;

import android.content.Context;

import com.xuhai.telescopes.domain.Net;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chudong on 2015/12/24.
 */
public class NetDao {
    public static final String TABLE_NAME = "net";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_TASK = "task";
    public static final String COLUMN_NAME_STATUS = "status";
    public static final String COLUMN_NAME_TOTAL_COUNT = "total_count";
    public static final String COLUMN_NAME_UNREAD_COUNT = "unread_count";
    public static final String COLUMN_NAME_TIME = "time";
    public static final String COLUMN_NAME_USERNAME ="username";
    public static final String COLUMN_NAME_SEAMAN_ROLE = "seaman_role";
    public static final String COLUMN_NAME_SUMMARY = "summary";
    public static final String COLUMN_NAME_SEAMEN = "seamen";

    public NetDao(Context context) {
    }

    public void saveNets(ArrayList<Net> nets){
        DemoDBManager.getInstance().saveNets(nets);
    }

    public ArrayList<Net> getNets(){
        return DemoDBManager.getInstance().getNets();
    }

    public void delectNet(String id){
        DemoDBManager.getInstance().deleteNet(id);

    }


}
