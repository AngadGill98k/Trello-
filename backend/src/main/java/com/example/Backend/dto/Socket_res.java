package com.example.Backend.dto;

import com.example.Backend.models.Member;
import com.example.Backend.models.Note;
import com.example.Backend.models.Projects;
import com.example.Backend.models.Task;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Socket_res {
    private String id;
    private String Project_name;
    private String UserName;
    private String Userid;
    private Member member;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getProject_name() {
        return Project_name;
    }
    public void setProject_name(String Project_name) {
        this.Project_name = Project_name;
    }
    public String getUserName() {
        return UserName;
    }
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
    public String getUserid() {
        return Userid;
    }
    public void setUserid(String Userid) {
        this.Userid = Userid;
    }
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }


    private Task task;
    private String from;
    private String to;

    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }


    private Note note;

    public Note getNote() {
        return note;
    }
    public void setNote(Note note) {
        this.note = note;
    }


    private String action;

    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }


    private Map<String, Set<String>> OnlineMember=new ConcurrentHashMap<>();
    public Map<String, Set<String>> getOnlineMember() {
        return OnlineMember;
    }
    public void setOnlineMember(Map<String, Set<String>> OnlineMember) {
        this.OnlineMember = OnlineMember;
    }


    private Projects project;
    public Projects getProject() {
        return project;
    }
    public void setProject(Projects project) {
        this.project = project;
    }
}
