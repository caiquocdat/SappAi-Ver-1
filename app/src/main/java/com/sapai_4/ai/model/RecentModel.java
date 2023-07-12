package com.sapai_4.ai.model;

import java.util.Date;

public class RecentModel {
    private int id;
    private Date date;
    private String content;

    public RecentModel(int id, Date date, String content) {
        this.id = id;
        this.date = date;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public int getDueDateAsInteger() {
        return (int) (date.getTime() / 1000);
    }
}
