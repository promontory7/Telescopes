package com.xuhai.telescopes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chudong on 2015/12/24.
 */
public class Net implements Parcelable{
    private String id;
    private String task;
    private String status;
    private String total_count;
    private String unread_count;
    private String time;
    private String username;
    private String seaman_role;
    private String summary;
    private ArrayList<Seaman> seamen;


    protected Net(Parcel in) {
        id = in.readString();
        task = in.readString();
        status = in.readString();
        total_count = in.readString();
        unread_count = in.readString();
        time = in.readString();
        username = in.readString();
        seaman_role = in.readString();
        summary = in.readString();
        seamen = in.createTypedArrayList(Seaman.CREATOR);
    }

    public static final Creator<Net> CREATOR = new Creator<Net>() {
        @Override
        public Net createFromParcel(Parcel in) {
            return new Net(in);
        }

        @Override
        public Net[] newArray(int size) {
            return new Net[size];
        }
    };

    public Net() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(String unread_count) {
        this.unread_count = unread_count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeaman_role() {
        return seaman_role;
    }

    public void setSeaman_role(String seaman_role) {
        this.seaman_role = seaman_role;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArrayList<Seaman> getSeamen() {
        return seamen;
    }

    public void setSeamen(ArrayList<Seaman> seamen) {
        this.seamen = seamen;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(task);
        dest.writeString(status);
        dest.writeString(total_count);
        dest.writeString(unread_count);
        dest.writeString(time);
        dest.writeString(username);
        dest.writeString(seaman_role);
        dest.writeString(summary);
        dest.writeTypedList(seamen);
    }
}
