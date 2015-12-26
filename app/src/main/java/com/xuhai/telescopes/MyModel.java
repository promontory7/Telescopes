package com.xuhai.telescopes;

import android.content.Context;

import com.xuhai.easeui.domain.EaseUser;
import com.xuhai.telescopes.db.AllyDao;
import com.xuhai.telescopes.db.NetDao;
import com.xuhai.telescopes.db.UserDao;
import com.xuhai.telescopes.domain.Ally;
import com.xuhai.telescopes.domain.Net;
import com.xuhai.telescopes.domain.RobotUser;
import com.xuhai.telescopes.utils.PreferenceManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyModel {
    UserDao dao = null;
    AllyDao allyDao = null;
    NetDao netDao =null;
    protected Context context = null;
    protected Map<Key, Object> valueCache = new HashMap<Key, Object>();

    public MyModel(Context ctx) {
        context = ctx;
        PreferenceManager.init(context);
    }

    public boolean saveContactList(List<EaseUser> contactList) {
        UserDao dao = new UserDao(context);
        dao.saveContactList(contactList);
        return true;
    }

    public Map<String, EaseUser> getContactList() {
        UserDao dao = new UserDao(context);
        return dao.getContactList();
    }

    public void saveContact(EaseUser user) {
        UserDao dao = new UserDao(context);
        dao.saveContact(user);
    }

    public EaseUser getContact(String username) {
        UserDao dao = new UserDao(context);
        return dao.getContact(username);
    }

    /**
     * 设置当前用户的环信username
     *
     * @param username
     */
    public void setCurrentUserName(String username) {
        PreferenceManager.getInstance().setCurrentUserName(username);
    }

    /**
     * 获取当前用户的环信username
     */
    public String getCurrentUsernName() {
        return PreferenceManager.getInstance().getCurrentUsername();
    }

    public void setCurrentUserToken(String token) {
        PreferenceManager.getInstance().setCurrentUserToken(token);
    }

    public String getCurrentUserToken() {
        return PreferenceManager.getInstance().getCurrentUserToken();
    }

    public void setTeam(List<String> team) {
        if (dao == null) {
            dao = new UserDao(context);
        }

        dao.setTeam(team);
    }

    public List<String> getTeam() {

        if (dao == null) {
            dao = new UserDao(context);
        }
        return dao.getTeam();
    }

    public void setBlacklist(List<String> blacklist) {
        if (dao == null) {
            dao = new UserDao(context);
        }

        dao.setBlacklist(blacklist);
    }

    public List<String> getBlacklist() {
        if (dao == null) {
            dao = new UserDao(context);
        }
        return dao.getBlacklist();
    }

    public void saveNetsList(List<Net> nets){
        if(netDao==null){
            netDao = new NetDao(context);
        }
        netDao.saveNets(nets);
    }

    public List<Net> getNetsList(){
        if(netDao ==null){
            netDao = new NetDao(context);
        }
        return  netDao.getNets();
    }
    public void deleteNet(String id){
        if(netDao==null){
            netDao = new NetDao(context);
        }
        netDao.delectNet(id);
    }

    public void setAllies(List<Ally> allies) {
        if (allyDao == null) {
            allyDao = new AllyDao(context);
        }
        allyDao.saveAllies(allies);
    }

    public List<Ally> getAllies() {
        if (allyDao == null) {
            allyDao = new AllyDao(context);
        }
        return allyDao.getAllies();
    }

    public Map<String, RobotUser> getRobotList() {
        UserDao dao = new UserDao(context);
        return dao.getRobotUser();
    }

    public boolean saveRobotList(List<RobotUser> robotList) {
        UserDao dao = new UserDao(context);
        dao.saveRobotUser(robotList);
        return true;
    }

    public void setSettingMsgNotification(boolean paramBoolean) {
        PreferenceManager.getInstance().setSettingMsgNotification(paramBoolean);
        valueCache.put(Key.VibrateAndPlayToneOn, paramBoolean);
    }

    public boolean getSettingMsgNotification() {
        Object val = valueCache.get(Key.VibrateAndPlayToneOn);

        if (val == null) {
            val = PreferenceManager.getInstance().getSettingMsgNotification();
            valueCache.put(Key.VibrateAndPlayToneOn, val);
        }

        return (Boolean) (val != null ? val : true);
    }

    public void setSettingMsgSound(boolean paramBoolean) {
        PreferenceManager.getInstance().setSettingMsgSound(paramBoolean);
        valueCache.put(Key.PlayToneOn, paramBoolean);
    }

    public boolean getSettingMsgSound() {
        Object val = valueCache.get(Key.PlayToneOn);

        if (val == null) {
            val = PreferenceManager.getInstance().getSettingMsgSound();
            valueCache.put(Key.PlayToneOn, val);
        }

        return (Boolean) (val != null ? val : true);
    }

    public void setSettingMsgVibrate(boolean paramBoolean) {
        PreferenceManager.getInstance().setSettingMsgVibrate(paramBoolean);
        valueCache.put(Key.VibrateOn, paramBoolean);
    }

    public boolean getSettingMsgVibrate() {
        Object val = valueCache.get(Key.VibrateOn);

        if (val == null) {
            val = PreferenceManager.getInstance().getSettingMsgVibrate();
            valueCache.put(Key.VibrateOn, val);
        }

        return (Boolean) (val != null ? val : true);
    }

    public void setSettingMsgSpeaker(boolean paramBoolean) {
        PreferenceManager.getInstance().setSettingMsgSpeaker(paramBoolean);
        valueCache.put(Key.SpakerOn, paramBoolean);
    }

    public boolean getSettingMsgSpeaker() {
        Object val = valueCache.get(Key.SpakerOn);

        if (val == null) {
            val = PreferenceManager.getInstance().getSettingMsgSpeaker();
            valueCache.put(Key.SpakerOn, val);
        }

        return (Boolean) (val != null ? val : true);
    }


    public void setDisabledGroups(List<String> groups) {
        if (dao == null) {
            dao = new UserDao(context);
        }

        dao.setDisabledGroups(groups);
        valueCache.put(Key.DisabledGroups, groups);
    }

    public List<String> getDisabledGroups() {
        Object val = valueCache.get(Key.DisabledGroups);

        if (dao == null) {
            dao = new UserDao(context);
        }

        if (val == null) {
            val = dao.getDisabledGroups();
            valueCache.put(Key.DisabledGroups, val);
        }

        return (List<String>) val;
    }

    public void setDisabledIds(List<String> ids) {
        if (dao == null) {
            dao = new UserDao(context);
        }

        dao.setDisabledIds(ids);
        valueCache.put(Key.DisabledIds, ids);
    }

    public List<String> getDisabledIds() {
        Object val = valueCache.get(Key.DisabledIds);

        if (dao == null) {
            dao = new UserDao(context);
        }

        if (val == null) {
            val = dao.getDisabledIds();
            valueCache.put(Key.DisabledIds, val);
        }

        return (List<String>) val;
    }

    public void setGroupsSynced(boolean synced) {
        PreferenceManager.getInstance().setGroupsSynced(synced);
    }

    public boolean isGroupsSynced() {
        return PreferenceManager.getInstance().isGroupsSynced();
    }

    public void setContactSynced(boolean synced) {
        PreferenceManager.getInstance().setContactSynced(synced);
    }

    public boolean isContactSynced() {
        return PreferenceManager.getInstance().isContactSynced();
    }

    public void setBlacklistSynced(boolean synced) {
        PreferenceManager.getInstance().setBlacklistSynced(synced);
    }

    public boolean isBacklistSynced() {
        return PreferenceManager.getInstance().isBacklistSynced();
    }

    public void allowChatroomOwnerLeave(boolean value) {
        PreferenceManager.getInstance().setSettingAllowChatroomOwnerLeave(value);
    }

    public boolean isChatroomOwnerLeaveAllowed() {
        return PreferenceManager.getInstance().getSettingAllowChatroomOwnerLeave();
    }


    enum Key {
        VibrateAndPlayToneOn,
        VibrateOn,
        PlayToneOn,
        SpakerOn,
        DisabledGroups,
        DisabledIds,
    }
}
