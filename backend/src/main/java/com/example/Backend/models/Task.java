package com.example.Backend.models;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class Task {
    private String name;
    private String id;
    private ArrayList<Member> assignedto = new ArrayList<>();
    public String getName(){
        return name;
    }
    public String getId(){
        return id;
    }
    public ArrayList<Member> getMembers(){
        return assignedto;
    }

    public  void setName(String name){
        this.name = name;
    }
    public void setMembers(ArrayList<Member> members){
        this.assignedto = members;
    }
    public void addMember(Member member){
        assignedto.add(member);
    }
    public  void genId(){
        String id=new ObjectId().toString();
        this.id=id;
    }
    public void setId(String id){
        this.id = id;
    }


}
