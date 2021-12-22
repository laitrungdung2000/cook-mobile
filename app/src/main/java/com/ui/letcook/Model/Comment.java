package com.ui.letcook.Model;
public class Comment {
    private String iddish,imageuser,emailuser,comment;
    public Comment(){};

    public String getIddish() {
        return iddish;
    }

    public void setIddish(String iddish) {
        this.iddish = iddish;
    }

    public Comment(String iddish, String imageuser, String emailuser, String comment) {
        this.iddish = iddish;
        this.imageuser = imageuser;
        this.emailuser = emailuser;
        this.comment = comment;
    }



    public String getImageuser() {
        return imageuser;
    }

    public void setImageuser(String imageuser) {
        this.imageuser = imageuser;
    }

    public String getEmailuser() {
        return emailuser;
    }

    public void setEmailuser(String emailuser) {
        this.emailuser = emailuser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
