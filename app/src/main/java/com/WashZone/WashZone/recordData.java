package com.WashZone.WashZone;

public class recordData {
    private int USER_ID;
    private String USER_NAME;
    private String USER_NUMBER;
    private String USER_BIRTH;
    private String USER_SMSRECORD;

    public recordData(int USER_ID, String USER_NAME, String USER_NUMBER, String USER_BIRTH, String USER_SMSRECORD) {
        this.USER_ID = USER_ID;
        this.USER_NAME = USER_NAME;
        this.USER_NUMBER = USER_NUMBER;
        this.USER_BIRTH = USER_BIRTH;
        this.USER_SMSRECORD = USER_SMSRECORD;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public String getUSER_NUMBER() {
        return USER_NUMBER;
    }

    public String getUSER_BIRTH() {
        return USER_BIRTH;
    }

    public String getUSER_SMSRECORD() {
        return USER_SMSRECORD;
    }
}
