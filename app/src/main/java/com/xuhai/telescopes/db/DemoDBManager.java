package com.xuhai.telescopes.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.easemob.util.HanziToPinyin;
import com.xuhai.easeui.domain.EaseUser;
import com.xuhai.telescopes.Constant;
import com.xuhai.telescopes.MyApplication;
import com.xuhai.telescopes.domain.InviteMessage;
import com.xuhai.telescopes.domain.RobotUser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class DemoDBManager {
    static private DemoDBManager dbMgr = new DemoDBManager();
    private DbOpenHelper dbHelper;

    private DemoDBManager() {
        dbHelper = DbOpenHelper.getInstance(MyApplication.getInstance().getApplicationContext());
    }

    public static synchronized DemoDBManager getInstance() {
        if (dbMgr == null) {
            dbMgr = new DemoDBManager();
        }
        return dbMgr;
    }

    /**
     * 保存好友list
     *
     * @param contactList
     */
    synchronized public void saveContactList(List<EaseUser> contactList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(UserDao.TABLE_NAME, null, null);
            for (EaseUser user : contactList) {
                saveContactToDB(user, db);
            }
        }
    }

    /**
     * 获取好友list
     *
     * @return
     */
    synchronized public Map<String, EaseUser> getContactList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Map<String, EaseUser> users = new HashMap<String, EaseUser>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + UserDao.TABLE_NAME /* + " desc" */, null);
            while (cursor.moveToNext()) {

                EaseUser user = getContactFromDB(cursor);


                String headerName = null;
                if (!TextUtils.isEmpty(user.getNick())) {
                    headerName = user.getNick();
                } else {
                    headerName = user.getUsername();
                }

                if (user.getUsername().equals(Constant.NEW_FRIENDS_USERNAME) || user.getUsername().equals(Constant.GROUP_USERNAME)
                        || user.getUsername().equals(Constant.CHAT_ROOM) || user.getUsername().equals(Constant.CHAT_ROBOT)) {
                    user.setInitialLetter("");
                } else if (Character.isDigit(headerName.charAt(0))) {
                    user.setInitialLetter("#");
                } else {
                    user.setInitialLetter(HanziToPinyin.getInstance().get(headerName.substring(0, 1))
                            .get(0).target.substring(0, 1).toUpperCase());
                    char header = user.getInitialLetter().toLowerCase().charAt(0);
                    if (header < 'a' || header > 'z') {
                        user.setInitialLetter("#");
                    }
                }
                users.put(user.getUsername(), user);
            }
            cursor.close();
        }
        return users;
    }

    /**
     * 删除一个联系人
     *
     * @param username
     */
    synchronized public void deleteContact(String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(UserDao.TABLE_NAME, UserDao.COLUMN_NAME_USERNAME + " = ?", new String[]{username});
        }
    }

    /**
     * 保存一个联系人
     *
     * @param user
     */
    synchronized public void saveContact(EaseUser user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        saveContactToDB(user, db);

    }

    /**
     * 获得一个联系人
     *
     * @param username
     * @return
     */
    synchronized public EaseUser getContact(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        EaseUser user = new EaseUser();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + UserDao.TABLE_NAME + " where" + UserDao.COLUMN_NAME_USERNAME + "=" + username, null);
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_USERNAME)) == username) {
                    user = getContactFromDB(cursor);
                }
            }
        }
        return user;
    }

    /**
     * 存储分组列表
     *
     * @param team
     */
    synchronized public void setTeam(List<String> team) {
        setList(UserDao.COLUMN_NAME_TEAM, team);
    }

    /**
     * 获得分组列表
     *
     * @return
     */
    synchronized public List<String> getTeam() {
        return getList(UserDao.COLUMN_NAME_TEAM);
    }


    public void setDisabledGroups(List<String> groups) {
        setList(UserDao.COLUMN_NAME_DISABLED_GROUPS, groups);
    }

    public List<String> getDisabledGroups() {
        return getList(UserDao.COLUMN_NAME_DISABLED_GROUPS);
    }

    public void setDisabledIds(List<String> ids) {
        setList(UserDao.COLUMN_NAME_DISABLED_IDS, ids);
    }

    public List<String> getDisabledIds() {
        return getList(UserDao.COLUMN_NAME_DISABLED_IDS);
    }

    synchronized private void setList(String column, List<String> strList) {
        StringBuilder strBuilder = new StringBuilder();

        for (String hxid : strList) {
            strBuilder.append(hxid).append("$");
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(column, strBuilder.toString());

            db.update(UserDao.PREF_TABLE_NAME, values, null, null);
        }
    }

    synchronized private List<String> getList(String column) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + column + " from " + UserDao.PREF_TABLE_NAME, null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        String strVal = cursor.getString(0);
        if (strVal == null || strVal.equals("")) {
            return null;
        }

        cursor.close();

        String[] array = strVal.split("$");

        if (array != null && array.length > 0) {
            List<String> list = new ArrayList<String>();
            for (String str : array) {
                list.add(str);
            }

            return list;
        }

        return null;
    }

    /**
     * 保存message
     *
     * @param message
     * @return 返回这条messaged在db中的id
     */
    public synchronized Integer saveMessage(InviteMessage message) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int id = -1;
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(InviteMessgeDao.COLUMN_NAME_FROM, message.getFrom());
            values.put(InviteMessgeDao.COLUMN_NAME_GROUP_ID, message.getGroupId());
            values.put(InviteMessgeDao.COLUMN_NAME_GROUP_Name, message.getGroupName());
            values.put(InviteMessgeDao.COLUMN_NAME_REASON, message.getReason());
            values.put(InviteMessgeDao.COLUMN_NAME_TIME, message.getTime());
            values.put(InviteMessgeDao.COLUMN_NAME_STATUS, message.getStatus().ordinal());
            db.insert(InviteMessgeDao.TABLE_NAME, null, values);

            Cursor cursor = db.rawQuery("select last_insert_rowid() from " + InviteMessgeDao.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                id = cursor.getInt(0);
            }

            cursor.close();
        }
        return id;
    }

    /**
     * 更新message
     *
     * @param msgId
     * @param values
     */
    synchronized public void updateMessage(int msgId, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.update(InviteMessgeDao.TABLE_NAME, values, InviteMessgeDao.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(msgId)});
        }
    }

    /**
     * 获取messges
     *
     * @return
     */
    synchronized public List<InviteMessage> getMessagesList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<InviteMessage> msgs = new ArrayList<InviteMessage>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + InviteMessgeDao.TABLE_NAME + " desc", null);
            while (cursor.moveToNext()) {
                InviteMessage msg = new InviteMessage();
                int id = cursor.getInt(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_ID));
                String from = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_FROM));
                String groupid = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_GROUP_ID));
                String groupname = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_GROUP_Name));
                String reason = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_REASON));
                long time = cursor.getLong(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_TIME));
                int status = cursor.getInt(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_STATUS));

                msg.setId(id);
                msg.setFrom(from);
                msg.setGroupId(groupid);
                msg.setGroupName(groupname);
                msg.setReason(reason);
                msg.setTime(time);
                if (status == InviteMessage.InviteMesageStatus.BEINVITEED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.BEINVITEED);
                else if (status == InviteMessage.InviteMesageStatus.BEAGREED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.BEAGREED);
                else if (status == InviteMessage.InviteMesageStatus.BEREFUSED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.BEREFUSED);
                else if (status == InviteMessage.InviteMesageStatus.AGREED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.AGREED);
                else if (status == InviteMessage.InviteMesageStatus.REFUSED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.REFUSED);
                else if (status == InviteMessage.InviteMesageStatus.BEAPPLYED.ordinal()) {
                    msg.setStatus(InviteMessage.InviteMesageStatus.BEAPPLYED);
                }
                msgs.add(msg);
            }
            cursor.close();
        }
        return msgs;
    }

    /**
     * 删除要求消息
     *
     * @param from
     */
    synchronized public void deleteMessage(String from) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(InviteMessgeDao.TABLE_NAME, InviteMessgeDao.COLUMN_NAME_FROM + " = ?", new String[]{from});
        }
    }

    synchronized int getUnreadNotifyCount() {
        int count = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select " + InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " from " + InviteMessgeDao.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }

    synchronized void setUnreadNotifyCount(int count) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT, count);

            db.update(InviteMessgeDao.TABLE_NAME, values, null, null);
        }
    }

    synchronized public void closeDB() {
        if (dbHelper != null) {
            dbHelper.closeDB();
        }
        dbMgr = null;
    }


    /**
     * Save Robot list
     */
    synchronized public void saveRobotList(List<RobotUser> robotList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(UserDao.ROBOT_TABLE_NAME, null, null);
            for (RobotUser item : robotList) {
                ContentValues values = new ContentValues();
                values.put(UserDao.ROBOT_COLUMN_NAME_ID, item.getUsername());
                if (item.getNick() != null)
                    values.put(UserDao.ROBOT_COLUMN_NAME_NICK, item.getNick());
                if (item.getAvatar() != null)
                    values.put(UserDao.ROBOT_COLUMN_NAME_AVATAR, item.getAvatar());
                db.replace(UserDao.ROBOT_TABLE_NAME, null, values);
            }
        }
    }

    /**
     * load robot list
     */
    synchronized public Map<String, RobotUser> getRobotList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Map<String, RobotUser> users = null;
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + UserDao.ROBOT_TABLE_NAME, null);
            if (cursor.getCount() > 0) {
                users = new HashMap<String, RobotUser>();
            }
            ;
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex(UserDao.ROBOT_COLUMN_NAME_ID));
                String nick = cursor.getString(cursor.getColumnIndex(UserDao.ROBOT_COLUMN_NAME_NICK));
                String avatar = cursor.getString(cursor.getColumnIndex(UserDao.ROBOT_COLUMN_NAME_AVATAR));
                RobotUser user = new RobotUser(username);
                user.setNick(nick);
                user.setAvatar(avatar);
                String headerName = null;
                if (!TextUtils.isEmpty(user.getNick())) {
                    headerName = user.getNick();
                } else {
                    headerName = user.getUsername();
                }
                if (Character.isDigit(headerName.charAt(0))) {
                    user.setInitialLetter("#");
                } else {
                    user.setInitialLetter(HanziToPinyin.getInstance().get(headerName.substring(0, 1)).get(0).target
                            .substring(0, 1).toUpperCase());
                    char header = user.getInitialLetter().toLowerCase().charAt(0);
                    if (header < 'a' || header > 'z') {
                        user.setInitialLetter("#");
                    }
                }

                users.put(username, user);
            }
            cursor.close();
        }
        return users;
    }


    public void saveContactToDB(EaseUser user, SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(UserDao.COLUMN_NAME_USERID, user.getUserId());
        values.put(UserDao.COLUMN_NAME_USERNAME, user.getUsername());
        if (user.getNick() != null)
            values.put(UserDao.COLUMN_NAME_NICK, user.getNick());
        if (user.getAvatar() != null)
            values.put(UserDao.COLUMN_NAME_AVATAR, user.getAvatar());
        if (user.getRename() != null)
            values.put(UserDao.COLUMN_NAME_RENAME, user.getRename());
        if (user.getIdcard() != null)
            values.put(UserDao.COLUMN_NAME_IDCARD, user.getIdcard());
        if (user.getEmail() != null)
            values.put(UserDao.COLUMN_NAME_EMAIL, user.getEmail());
        if (user.getSignature() != null)
            values.put(UserDao.COLUMN_NAME_SIGNATURE, user.getSignature());
        if (user.getEffect() != null)
            values.put(UserDao.COLUMN_NAME_EFFECT, user.getEffect());
        if (user.getLevel() != null)
            values.put(UserDao.COLUMN_NAME_LEVEL, user.getLevel());
        if (user.getPearl() != null)
            values.put(UserDao.COLUMN_NAME_PEARL, user.getPearl());
        if (user.getCountry() != null)
            values.put(UserDao.COLUMN_NAME_COUNTRY, user.getCountry());
        if (user.getProvince() != null)
            values.put(UserDao.COLUMN_NAME_PROVINCE, user.getProvince());
        if (user.getCity() != null)
            values.put(UserDao.COLUMN_NAME_CITY, user.getCity());
        if (user.getArea() != null)
            values.put(UserDao.COLUMN_NAME_AREA, user.getArea());
        if (user.getAddr() != null)
            values.put(UserDao.COLUMN_NAME_ADDR, user.getAddr());
        if (user.getSchool() != null)
            values.put(UserDao.COLUMN_NAME_SCHOOL, user.getSchool());
        if (user.getSchool_state() != null)
            values.put(UserDao.COLUMN_NAME_SCHOOL_STATE, user.getSchool_state());
        if (user.getAuthentication() != (-1))
            values.put(UserDao.COLUMN_NAME_AUTHENTICATION, user.getAuthentication());
        if (user.getBirthday() != null)
            values.put(UserDao.COLUMN_NAME_BIRTHDAY, user.getBirthday());
        if (user.getMobile() != null)
            values.put(UserDao.COLUMN_NAME_MOBILE, user.getMobile());
        if (user.getGender() != (-1))
            values.put(UserDao.COLUMN_NAME_GENDER, user.getGender());
        if (user.getToken() != null)
            values.put(UserDao.COLUMN_NAME_TOKEN, user.getToken());
        if (user.getLast_login_ip() != null)
            values.put(UserDao.COLUMN_NAME_LAST_LOGIN_IN_IP, user.getLast_login_ip());
        if (user.getRegister_type() != null)
            values.put(UserDao.COLUMN_NAME_REGISTER_TYPE, user.getRegister_type());
        if (user.getCreated_at() != null)
            values.put(UserDao.COLUMN_NAME_CREATE_AT, user.getCreated_at());
        if (user.getRole_id() != null)
            values.put(UserDao.COLUMN_NAME_ROLE_ID, user.getRole_id());
        if (user.getRole_name() != null)
            values.put(UserDao.COLUMN_NAME_ROLE_NAME, user.getRole_name());
        if (user.getRole_description() != null)
            values.put(UserDao.COLUMN_NAME_ROLE_DESCRIPTION, user.getRole_description());

        if (db.isOpen()) {
            db.replace(UserDao.TABLE_NAME, null, values);
        }
    }

    public EaseUser getContactFromDB(Cursor cursor) {
        String userid = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_USERID));
        String username = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_USERNAME));
        String nick = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NICK));
        String avatar = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_AVATAR));
        String rename = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_RENAME));
        String idcard = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_IDCARD));
        String email = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_EMAIL));
        String signature = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_SIGNATURE));
        String effect = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_EFFECT));
        String level = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_LEVEL));
        String pearl = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_PEARL));
        String country = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_COUNTRY));
        String province = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_PROVINCE));
        String city = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_CITY));
        String area = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_AREA));
        String addr = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_ADDR));
        String school = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_SCHOOL));
        String school_state = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_SCHOOL_STATE));
        int authenticaton = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_AUTHENTICATION));
        String mobile = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_MOBILE));
        String birthday = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_BIRTHDAY));
        int gender = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_GENDER));
        String token = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_TOKEN));
        String last_login_ip = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_LAST_LOGIN_IN_IP));
        String register_type = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_REGISTER_TYPE));
        String create_at = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_CREATE_AT));
        String role_id = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_ROLE_ID));
        String role_name = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_ROLE_NAME));
        String role_description = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_ROLE_DESCRIPTION));

        EaseUser user = new EaseUser(username);
        user.setUserId(userid);
        user.setUsername(username);
        user.setNick(nick);
        user.setAvatar(avatar);
        user.setRename(rename);
        user.setIdcard(idcard);
        user.setEmail(email);
        user.setSignature(signature);
        user.setEffect(effect);
        user.setLevel(level);
        user.setPearl(pearl);
        user.setCountry(country);
        user.setProvince(province);
        user.setCity(city);
        user.setArea(area);
        user.setAddr(addr);
        user.setSchool(school);
        user.setSchool_state(school_state);
        user.setAuthentication(authenticaton);
        user.setBirthday(birthday);
        user.setMobile(mobile);
        user.setGender(gender);
        user.setToken(token);
        user.setLast_login_ip(last_login_ip);
        user.setRegister_type(register_type);
        user.setCreated_at(create_at);
        user.setRole_id(role_id);
        user.setRole_name(role_name);
        user.setRole_description(role_description);
        return user;
    }
}