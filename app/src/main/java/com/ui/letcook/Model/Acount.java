package com.ui.letcook.Model;

import java.util.ArrayList;

public class Acount {
    private String email,uid,image,username;
    private ArrayList<String> saved;



    private int sobaidang;
    public Acount(){};
    public Acount(String email, String uid, String image,String username, ArrayList<String> saved,int sobaidang) {
        this.email = email;
        this.uid = uid;
        this.image = image;
        this.username= username;
        this.saved = saved;
        this.sobaidang=sobaidang;
    }
    public int getSobaidang() {
        return sobaidang;
    }

    public void setSobaidang(int sobaidang) {
        this.sobaidang = sobaidang;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getSaved() {
        return saved;
    }

    public void setSaved(ArrayList<String> saved) {
        this.saved = saved;
    }
}
