package com.WashZone.WashZone;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Data01 {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String phonenumber;

    public Data01(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public String toString() {
        return "Data01{" +
                "id=" + id +
                ", phonenumber='" + phonenumber + '\'' +
                '}';
    }
}
