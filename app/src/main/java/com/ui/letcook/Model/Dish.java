package com.ui.letcook.Model;
public class Dish {
    private String image;
    private String namedish;
    private String emailuser;
    private String imageuser;
    private String make;
    private String date;
    private int view;
    private String id;
    private int solike;
    private String mota;
    private String nguyenlieu;

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getNguyenlieu() {
        return nguyenlieu;
    }

    public void setNguyenlieu(String nguyenlieu) {
        this.nguyenlieu = nguyenlieu;
    }

    public  Dish(){}
    public Dish(String image, String namedish,String mota,String nguyenlieu, String emailuser, String imageuser, String make, String date, int view,String id,int solike) {
        this.image = image;
        this.mota=mota;
        this.nguyenlieu=nguyenlieu;
        this.namedish = namedish;
        this.emailuser = emailuser;
        this.imageuser = imageuser;
        this.make = make;
        this.date = date;
        this.view=view;
        this.id=id;
        this.solike=solike;
    }

    public int getSolike() {
        return solike;
    }

    public void setSolike(int solike) {
        this.solike = solike;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNamedish() {
        return namedish;
    }

    public void setNamedish(String namedish) {
        this.namedish = namedish;
    }

    public String getEmailuser() {
        return emailuser;
    }

    public void setEmailuser(String emailuser) {
        this.emailuser = emailuser;
    }

    public String getImageuser() {
        return imageuser;
    }

    public void setImageuser(String imageuser) {
        this.imageuser = imageuser;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
