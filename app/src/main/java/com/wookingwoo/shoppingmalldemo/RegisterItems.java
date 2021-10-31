package com.wookingwoo.shoppingmalldemo;

public class RegisterItems {
    private String title;
    private String userInfo;


    public RegisterItems(String title, String userInfo) {
        this.title = title;
        this.userInfo = userInfo;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}
