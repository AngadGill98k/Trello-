package com.example.Backend.models;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class Task {
    private String name;
    private String id;
    private ArrayList<String> assignedto = new ArrayList<>();
    public String getName(){
        return name;
    }
    public String getId(){
        return id;
    }
    public ArrayList<String> getMembers(){
        return assignedto;
    }

    public  void setName(String name){
        this.name = name;
    }
    public void setMembers(ArrayList<String> members){
        this.assignedto = members;
    }
    public void addMember(String member){
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
