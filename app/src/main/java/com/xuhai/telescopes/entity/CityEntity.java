package com.xuhai.telescopes.entity;

import java.util.ArrayList;

/**
 * Created by chudong on 2015/12/5.
 */
public class CityEntity {

    private String city_name;
    private ArrayList<String> school;

    public ArrayList<String> getSchool() {
        return school;
    }

    public void setSchool(ArrayList<String> school) {
        this.school = school;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
