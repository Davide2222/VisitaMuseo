package com.example.visitamuseo.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class User {

    @PrimaryKey @NotNull
    private String uid;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "type")
    private String type;

    public User(String uid, String password, String type) {
        this.uid = uid;
        this.password = password;
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
