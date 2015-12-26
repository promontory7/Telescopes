package com.xuhai.telescopes.entity;

import java.util.ArrayList;

/**
 * Created by chudong on 2015/12/5.
 */
public class ProvinceEntity {
    private String province_name;
    private ArrayList<CityEntity> citys;

    public ArrayList<CityEntity> getCitys() {
        return citys;
    }

    public void setCitys(ArrayList<CityEntity> citys) {
        this.citys = citys;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }
}
