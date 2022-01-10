package com.example.tripper.Databases;

public class ResponseModel {


    private String result;
    private String message;
    private UserHelperClass user;

    public ResponseModel(String message) {
        this.message = message;
    }
    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
    public void setResult(String result) {
        this.result = result;
    }

    public void setUser(UserHelperClass user) {
        this.user = user;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public UserHelperClass getUser() {
        return user;
    }
}
