package com.example.tripper.Databases;

public class UserHelperClass {
    String fullName,username,email,phoneNo,password,date,gender,userID,createdDate;

    public UserHelperClass() {
    }

    public UserHelperClass(String userID,String fullName, String username, String email, String password, String date, String gender, String phoneNo,String createdDate) {
        this.userID=userID;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.date = date;
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.createdDate=createdDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
