package com.xuhai.telescopes.domain;

import java.util.ArrayList;

/**
 * Created by chudong on 2015/12/27.
 */
public class MatchingRole {

    private String seaman_role_name;
    private String seaman_role_id;
    private String unread_count;

    private ArrayList<MatchingNet> matchingNets;



    public String getSeaman_role_name() {
        return seaman_role_name;
    }

    public void setSeaman_role_name(String seaman_role_name) {
        this.seaman_role_name = seaman_role_name;
    }

    public String getSeaman_role_id() {
        return seaman_role_id;
    }

    public void setSeaman_role_id(String seaman_role_id) {
        this.seaman_role_id = seaman_role_id;
    }

    public String getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(String unread_count) {
        this.unread_count = unread_count;
    }

    public ArrayList<MatchingNet> getMatchingNets() {
        return matchingNets;
    }

    public void setMatchingNets(ArrayList<MatchingNet> matchingNets) {
        this.matchingNets = matchingNets;
    }
}

