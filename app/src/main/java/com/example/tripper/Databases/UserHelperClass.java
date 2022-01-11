package com.example.tripper.Databases;

public class UserHelperClass {
    String name,username,email,phone,password,dateOfBirth,gender,uid,createdDate;

    public UserHelperClass() {
    }

    public UserHelperClass(String uid,String name, String username, String email, String password, String dateOfBirth, String gender, String phone,String createdDate) {
        this.uid=uid;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phone;
        this.createdDate=createdDate;
    }

    public String getUserID() {
        return uid;
    }

    public void setUserID(String uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return name;
    }

    public void setFullName(String fullName) {
        this.name = name;
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
        return phone;
    }

    public void setPhoneNo(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return dateOfBirth;
    }

    public void setDate(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
