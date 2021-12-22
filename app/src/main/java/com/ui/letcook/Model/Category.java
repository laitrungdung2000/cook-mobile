package com.ui.letcook.Model;

public class Category {
    private String name;
    private String image;
    private String code;

    public Category(String name, String image, String code) {
        this.name = name;
        this.image = image;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category(){
    }
}
