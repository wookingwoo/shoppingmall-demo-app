package com.wookingwoo.shoppingmalldemo;

public class UserAccount {
    private String idToken; // Firebase UID
    private String emailId;
    private String password; // test 목적 코드 (제거해도됨)
    private String userName;

    public UserAccount() {
    }


    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
