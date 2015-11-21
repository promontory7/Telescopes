
package com.xuhai.telescopes.db;

import android.content.Context;

import com.xuhai.easeui.domain.EaseUser;
import com.xuhai.telescopes.domain.RobotUser;

import java.util.List;
import java.util.Map;

public class UserDao {
    public static final String TABLE_NAME = "uers";
    public static final String COLUMN_NAME_USERID = "userid";
    public static final String COLUMN_NAME_USERNAME = "username";
    public static final String COLUMN_NAME_NICK = "nick";
    public static final String COLUMN_NAME_AVATAR = "avatar";
    public static final String COLUMN_NAME_RENAME = "rename";
    public static final String COLUMN_NAME_IDCARD = "idcard";
    public static final String COLUMN_NAME_EMAIL = "email";
    public static final String COLUMN_NAME_SIGNATURE = "signature";
    public static final String COLUMN_NAME_EFFECT = "ettect";
    public static final String COLUMN_NAME_LEVEL = "level";
    public static final String COLUMN_NAME_PEARL = "pearl";
    public static final String COLUMN_NAME_COUNTRY = "country";
    public static final String COLUMN_NAME_PROVINCE = "province";
    public static final String COLUMN_NAME_CITY = "city";
    public static final String COLUMN_NAME_AREA = "area";
    public static final String COLUMN_NAME_ADDR = "addr";
    public static final String COLUMN_NAME_SCHOOL = "school";
    public static final String COLUMN_NAME_SCHOOL_STATE = "school_state";
    public static final String COLUMN_NAME_AUTHENTICATION = "authentication";
    public static final String COLUMN_NAME_BIRTHDAY = "birthday";
    public static final String COLUMN_NAME_MOBILE = "mobile";
    public static final String COLUMN_NAME_GENDER = "gender";
    public static final String COLUMN_NAME_TOKEN = "token";
    public static final String COLUMN_NAME_LAST_LOGIN_IN_IP = "last_login_ip";
    public static final String COLUMN_NAME_REGISTER_TYPE = "register_type";
    public static final String COLUMN_NAME_CREATE_AT = "create_at";
    public static final String COLUMN_NAME_ROLE_ID = "role_id";
    public static final String COLUMN_NAME_ROLE_NAME = "role_name";
    public static final String COLUMN_NAME_ROLE_DESCRIPTION = "description";

    public static final String PREF_TABLE_NAME = "pref";
    public static final String COLUMN_NAME_DISABLED_GROUPS = "disabled_groups";
    public static final String COLUMN_NAME_DISABLED_IDS = "disabled_ids";
    public static final String COLUMN_NAME_TEAM = "team";
    public static final String COLUMN_NAME_TEAM_USERS = "team_users";

    public static final String ROBOT_TABLE_NAME = "robots";
    public static final String ROBOT_COLUMN_NAME_ID = "username";
    public static final String ROBOT_COLUMN_NAME_NICK = "nick";
    public static final String ROBOT_COLUMN_NAME_AVATAR = "avatar";


    public UserDao(Context context) {
    }

    /**
     * 保存好友list
     *
     * @param contactList
     */
    public void saveContactList(List<EaseUser> contactList) {
        DemoDBManager.getInstance().saveContactList(contactList);
    }

    /**
     * 获取好友list
     *
     * @return
     */
    public Map<String, EaseUser> getContactList() {

        return DemoDBManager.getInstance().getContactList();
    }

    /**
     * 删除一个联系人
     *
     * @param username
     */
    public void deleteContact(String username) {
        DemoDBManager.getInstance().deleteContact(username);
    }

    /**
     * 保存一个联系人
     *
     * @param user
     */
    public void saveContact(EaseUser user) {
        DemoDBManager.getInstance().saveContact(user);
    }

    /**
     * 获得一个联系人
     *
     * @param username
     * @return
     */
    public EaseUser getContact(String username) {
        return DemoDBManager.getInstance().getContact(username);
    }

    public void setDisabledGroups(List<String> groups) {
        DemoDBManager.getInstance().setDisabledGroups(groups);
    }

    public List<String> getDisabledGroups() {
        return DemoDBManager.getInstance().getDisabledGroups();
    }

    /**
     * 保存分组列表
     *
     * @param team
     */
    public void setTeam(List<String> team) {
        DemoDBManager.getInstance().setTeam(team);
    }

    public List<String> getTeam(){
        return DemoDBManager.getInstance().getTeam();
    }

    public void setDisabledIds(List<String> ids) {
        DemoDBManager.getInstance().setDisabledIds(ids);
    }

    public List<String> getDisabledIds() {
        return DemoDBManager.getInstance().getDisabledIds();
    }

    public Map<String, RobotUser> getRobotUser() {
        return DemoDBManager.getInstance().getRobotList();
    }

    public void saveRobotUser(List<RobotUser> robotList) {
        DemoDBManager.getInstance().saveRobotList(robotList);
    }
}
