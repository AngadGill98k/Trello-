package com.example.Backend.dto;

public class Login_dto {
    private String name;
    private String mail;
    private String pass;

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPass(String paas) {
        this.pass = paas;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
