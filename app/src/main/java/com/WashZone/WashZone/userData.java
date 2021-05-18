package com.WashZone.WashZone;

public class userData {
    private int USER_ID;
    private String USER_NAME;
    private String USER_NUMBER;
    private String USER_BIRTH;
    private String USER_CAR;
    private String USER_CARBUMBER;
    private int USER_EVENTSTACK;

    public userData(int USER_ID, String USER_NAME, String USER_NUMBER, String USER_BIRTH, String USER_CAR, String USER_CARBUMBER, int USER_EVENTSTACK) {
        this.USER_ID = USER_ID;
        this.USER_NAME = USER_NAME;
        this.USER_NUMBER = USER_NUMBER;
        this.USER_BIRTH = USER_BIRTH;
        this.USER_CAR = USER_CAR;
        this.USER_CARBUMBER = USER_CARBUMBER;
        this.USER_EVENTSTACK = USER_EVENTSTACK;
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

    public String getUSER_CAR() {
        return USER_CAR;
    }

    public String getUSER_CARBUMBER() {
        return USER_CARBUMBER;
    }

    public int getUSER_EVENTSTACK() {
        return USER_EVENTSTACK;
    }
}
