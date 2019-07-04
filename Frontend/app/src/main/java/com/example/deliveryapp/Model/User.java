package com.example.deliveryapp.Model;

public class User {
    private int id, userType;
    private String username, password, nickname, company;

    public User() {
    }

    public User(int id, int userType) {
        this.id = id;
        this.userType = userType;
    }


    public User(int id, int userType, String username, String password, String nickname, String company) {
        this.id = id;
        this.userType = userType;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
