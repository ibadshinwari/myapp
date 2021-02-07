package com.example.universityportaluser.model_classes;

public class UserRecord {
    String fullname,username,password,email,phonenumber;

    String uid;

    public UserRecord() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }





    public UserRecord(String fullname, String username, String password, String email, String phonenumber) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
