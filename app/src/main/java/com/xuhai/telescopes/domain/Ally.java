package com.xuhai.telescopes.domain;

import java.util.List;

/**
 * Created by chudong on 2015/11/24.
 */
public class Ally {
    private String id;
    private String name;
    private int size;
    private String user_id;
    private String huanxin_group_id;
    private String description;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHuanxin_group_id() {
        return huanxin_group_id;
    }

    public void setHuanxin_group_id(String huanxin_group_id) {
        this.huanxin_group_id = huanxin_group_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
