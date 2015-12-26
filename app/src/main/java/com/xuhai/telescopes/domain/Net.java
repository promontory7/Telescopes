package com.xuhai.telescopes.domain;

import java.util.List;

/**
 * Created by chudong on 2015/12/24.
 */
public class Net {
    private String id;
    private String task;
    private String status;
    private String total_count;
    private String unread_count;
    private String time;
    private String username;
    private String seaman_role;
    private String summary;
    private List<Seaman> seamen;

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

    public List<Seaman> getSeamen() {
        return seamen;
    }

    public void setSeamen(List<Seaman> seamen) {
        this.seamen = seamen;
    }
}
