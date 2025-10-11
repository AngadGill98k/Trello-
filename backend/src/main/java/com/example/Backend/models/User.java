package com.example.Backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String mail;
    private String pass;
    private ArrayList<String> friends = new ArrayList<>();
    @DBRef
    private ArrayList<Projects> projects = new ArrayList<>();

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public String getId(){
        return id;
    }

    public ArrayList<Projects> getProjects(){return this.projects;}

    public ArrayList<String> getFriends(){return this.friends;}

    public void setName(String name) {
        this.name = name;
    }

    public void setPaas(String paas) {
        this.pass = paas;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setProjetcs(Projects project){
        this.projects.add(project);
    }

    public void setFriends(String friends) {
        this.friends.add(friends);
    }
}

