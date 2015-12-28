package com.xuhai.telescopes.domain;

/**
 * Created by chudong on 2015/12/28.
 */
public class MatchingNet {

    private String net_id;
    private String task;
    private String user_name;
    private String is_read;
    private String is_friend;

    private String seaman_role_id;//需要我的什么角色

    public String getNet_id() {
        return net_id;
    }

    public void setNet_id(String net_id) {
        this.net_id = net_id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(String is_friend) {
        this.is_friend = is_friend;
    }

    public String getSeaman_role_id() {
        return seaman_role_id;
    }

    public void setSeaman_role_id(String seaman_role_id) {
        this.seaman_role_id = seaman_role_id;
    }
}
