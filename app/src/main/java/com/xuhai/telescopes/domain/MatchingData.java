package com.xuhai.telescopes.domain;

import java.util.ArrayList;

/**
 * Created by chudong on 2015/12/28.
 */
public class MatchingData {

    private ArrayList<MatchingRole> matchingRoles;
    private ArrayList<MatchingNet> matchingNeedMeNets;

    public ArrayList<MatchingRole> getMatchingRoles() {
        return matchingRoles;
    }

    public void setMatchingRoles(ArrayList<MatchingRole> matchingRoles) {
        this.matchingRoles = matchingRoles;
    }

    public ArrayList<MatchingNet> getMatchingNeedMeNets() {
        return matchingNeedMeNets;
    }

    public void setMatchingNeedMeNets(ArrayList<MatchingNet> matchingNeedMeNets) {
        this.matchingNeedMeNets = matchingNeedMeNets;
    }
}
