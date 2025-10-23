package com.example.Backend.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Note {
    private String id;
    private String note;
    private String user;;
    private String userid;
    public Note() {}
    public Note(String note, String user, String userid) {
        this.note = note;
        this.user = user;
        this.userid = userid;
        this.id= new ObjectId().toString();
    }

    public String getId() {
        return id;
    }
    public String getNote() {
        return note;
    }
    public String getUser() {
        return user;
    }
    public String getUserid() {
        return userid;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }

}
