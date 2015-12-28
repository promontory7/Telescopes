package com.xuhai.telescopes.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chudong on 2015/12/24.
 */
public class Seaman implements Parcelable{
    private String id;
    private String seaman_role_id;
    private String seaman_role_name;

    public Seaman(String id, String seaman_role_id, String seaman_role_name) {
        this.id = id;
        this.seaman_role_id = seaman_role_id;
        this.seaman_role_name = seaman_role_name;
    }

    public Seaman() {
    }


    protected Seaman(Parcel in) {
        id = in.readString();
        seaman_role_id = in.readString();
        seaman_role_name = in.readString();
    }

    public static final Creator<Seaman> CREATOR = new Creator<Seaman>() {
        @Override
        public Seaman createFromParcel(Parcel in) {
            return new Seaman(in);
        }

        @Override
        public Seaman[] newArray(int size) {
            return new Seaman[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(seaman_role_id);
        dest.writeString(seaman_role_name);
    }
}
