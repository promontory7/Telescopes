package com.xuhai.telescopes.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chudong on 2015/12/26.
 */
public class Location implements Parcelable {


    private String school_id;
    private String province;
    private String city;

    public Location() {
    }

    public Location(String school_id, String province, String city) {
        this.school_id = school_id;
        this.province = province;
        this.city = city;
    }



    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    protected Location(Parcel in) {
        school_id = in.readString();
        province = in.readString();
        city = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(school_id);
        dest.writeString(province);
        dest.writeString(city);
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}

