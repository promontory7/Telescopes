package com.xuhai.telescopes.domain;

/**
 * Created by chudong on 2015/12/24.
 */
public class Seaman {
    private String id;
    private String seaman_role_id ;
    private String seaman_role_name ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeaman_role_id() {
        return seaman_role_id;
    }

    public void setSeaman_role_id(String seaman_role_id) {
        this.seaman_role_id = seaman_role_id;
    }

    public String getSeaman_role_name() {
        return seaman_role_name;
    }

    public void setSeaman_role_name(String seaman_role_name) {
        this.seaman_role_name = seaman_role_name;
    }
}
