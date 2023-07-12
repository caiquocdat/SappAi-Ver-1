package com.sapai_4.ai.model;

import java.util.ArrayList;
import java.util.Date;

public class RecentCopyModel {
    private int id;
    private Date timeCreateChat;
    private ArrayList<MessageCopyModel> listMessage;

    public RecentCopyModel(int id, Date timeCreateChat, ArrayList<MessageCopyModel> listMessage) {
        this.id = id;
        this.timeCreateChat = timeCreateChat;
        this.listMessage = listMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimeCreateChat() {
        return timeCreateChat;
    }

    public void setTimeCreateChat(Date timeCreateChat) {
        this.timeCreateChat = timeCreateChat;
    }

    public ArrayList<MessageCopyModel> getListMessage() {
        return listMessage;
    }

    public void setListMessage(ArrayList<MessageCopyModel> listMessage) {
        this.listMessage = listMessage;
    }
    public int getDueDateAsInteger() {
        return (int) (timeCreateChat.getTime() / 1000);
    }
}
