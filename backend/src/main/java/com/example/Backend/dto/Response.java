package com.example.Backend.dto;

public class Response<T> {
    private boolean msg;
    private String message;
    private String access_token;
    private String refresh_token;
    private T data;

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setData(T data) {
        this.data = data;
    }
    public T getData() {
        return data;
    }
    public String getAccess_token() {
        return access_token;
    }
    public String getRefresh_token() {
        return refresh_token;
    }
    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public void setMsg(boolean msg) {
        this.msg = msg;
    }
    public boolean isMsg() {
        return msg;
    }
}
