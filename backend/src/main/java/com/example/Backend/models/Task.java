package com.example.Backend.models;

import org.bson.types.ObjectId;

public class Task {
    private String name;
    private String id;

    public String getName(){
        return name;
    }
    public String getId(){
        return id;
    }

    public  void setName(String name){
        this.name = name;
    }
    public  void genId(){
        String id=new ObjectId().toString();
        this.id=id;
    }
    public void setId(String id){
        this.id = id;
    }


}
