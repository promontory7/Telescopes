/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xuhai.telescopes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xuhai.telescopes.MyHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 5;
	private static DbOpenHelper instance;

	private static final String USERNAME_TABLE_CREATE = "CREATE TABLE "
			+ UserDao.TABLE_NAME + " ("
			+ UserDao.COLUMN_NAME_USERID + " TEXT PRIMARY KEY ,"
			+ UserDao.COLUMN_NAME_NICK + " TEXT, "
			+ UserDao.COLUMN_NAME_AVATAR + " TEXT, "
			+ UserDao.COLUMN_NAME_USERNAME + " TEXT,"
			+ UserDao.COLUMN_NAME_RENAME + " TEXT ,"
			+ UserDao.COLUMN_NAME_IDCARD + " TEXT ,"
			+ UserDao.COLUMN_NAME_EMAIL + " TEXT ,"
			+ UserDao.COLUMN_NAME_SIGNATURE + " TEXT ,"
			+ UserDao.COLUMN_NAME_EFFECT + " TEXT ,"
			+ UserDao.COLUMN_NAME_LEVEL + " TEXT ,"
			+ UserDao.COLUMN_NAME_PEARL + " TEXT ,"
			+ UserDao.COLUMN_NAME_COUNTRY + " TEXT ,"
			+ UserDao.COLUMN_NAME_PROVINCE + " TEXT ,"
			+ UserDao.COLUMN_NAME_CITY + " TEXT ,"
			+ UserDao.COLUMN_NAME_AREA + " TEXT ,"
			+ UserDao.COLUMN_NAME_ADDR + " TEXT ,"
			+ UserDao.COLUMN_NAME_SCHOOL + " TEXT ,"
			+ UserDao.COLUMN_NAME_SCHOOL_STATE + " TEXT ,"
			+ UserDao.COLUMN_NAME_AUTHENTICATION + " TEXT ,"
			+ UserDao.COLUMN_NAME_BIRTHDAY + " TEXT ,"
			+ UserDao.COLUMN_NAME_MOBILE + " TEXT ,"
			+ UserDao.COLUMN_NAME_GENDER + " TEXT ,"
			+ UserDao.COLUMN_NAME_TOKEN + " TEXT ,"
			+ UserDao.COLUMN_NAME_LAST_LOGIN_IN_IP + " TEXT ,"
			+ UserDao.COLUMN_NAME_REGISTER_TYPE + " TEXT ,"
			+ UserDao.COLUMN_NAME_CREATE_AT + " TEXT ,"
			+ UserDao.COLUMN_NAME_ROLE_ID + " TEXT ,"
			+ UserDao.COLUMN_NAME_ROLE_NAME + " TEXT ,"
			+ UserDao.COLUMN_NAME_ROLE_DESCRIPTION + " TEXT );";
	
	private static final String INIVTE_MESSAGE_TABLE_CREATE = "CREATE TABLE "
			+ InviteMessgeDao.TABLE_NAME + " ("
			+ InviteMessgeDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ InviteMessgeDao.COLUMN_NAME_FROM + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_FRIENDSHIP + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_GROUP_ID + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_GROUP_Name + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_REASON + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_STATUS + " INTEGER, "
			+ InviteMessgeDao.COLUMN_NAME_ISINVITEFROMME + " INTEGER, "
			+ InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " INTEGER, "
			+ InviteMessgeDao.COLUMN_NAME_TIME + " TEXT); ";
			
	private static final String ROBOT_TABLE_CREATE = "CREATE TABLE "
			+ UserDao.ROBOT_TABLE_NAME + " ("
			+ UserDao.ROBOT_COLUMN_NAME_ID + " TEXT PRIMARY KEY, "
			+ UserDao.ROBOT_COLUMN_NAME_NICK + " TEXT, "
			+ UserDao.ROBOT_COLUMN_NAME_AVATAR + " TEXT);";
			
	private static final String CREATE_PREF_TABLE = "CREATE TABLE "
            + UserDao.PREF_TABLE_NAME + " ("
            + UserDao.COLUMN_NAME_DISABLED_GROUPS + " TEXT, "
            + UserDao.COLUMN_NAME_DISABLED_IDS + " TEXT,"
			+ UserDao.COLUMN_NAME_TEAM+ " TEXT, "
			+ UserDao.COLUMN_NAME_TEAM_USERS + " TEXT);";
	
	private DbOpenHelper(Context context) {
		super(context, getUserDatabaseName(), null, DATABASE_VERSION);
	}
	
	public static DbOpenHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DbOpenHelper(context.getApplicationContext());
		}
		return instance;
	}
	
	private static String getUserDatabaseName() {
        return  MyHelper.getInstance().getCurrentUsernName() + "_demo.db";
    }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(USERNAME_TABLE_CREATE);
		db.execSQL(INIVTE_MESSAGE_TABLE_CREATE);
		db.execSQL(CREATE_PREF_TABLE);
		db.execSQL(ROBOT_TABLE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(oldVersion < 2){
		    db.execSQL("ALTER TABLE "+ UserDao.TABLE_NAME +" ADD COLUMN "+ 
		            UserDao.COLUMN_NAME_AVATAR + " TEXT ;");
		}
		
		if(oldVersion < 3){
		    db.execSQL(CREATE_PREF_TABLE);
        }
		if(oldVersion < 4){
			db.execSQL(ROBOT_TABLE_CREATE);
		}
		if(oldVersion < 5){
		    db.execSQL("ALTER TABLE " + InviteMessgeDao.TABLE_NAME + " ADD COLUMN " + 
		            InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " INTEGER ;");
		}
	}
	
	public void closeDB() {
	    if (instance != null) {
	        try {
	            SQLiteDatabase db = instance.getWritableDatabase();
	            db.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        instance = null;
	    }
	}
	
}
