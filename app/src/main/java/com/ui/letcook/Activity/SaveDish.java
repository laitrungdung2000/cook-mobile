package com.ui.letcook.Activity;

public class SaveDish {
    private String idsave;
    private String iddish;
    private String namedish;

    public SaveDish(){}

    public SaveDish(String idsave, String iddish, String namedish) {
        this.idsave = idsave;
        this.iddish = iddish;
        this.namedish = namedish;
    }

    public String getIdsave() {
        return idsave;
    }

    public void setIdsave(String idsave) {
        this.idsave = idsave;
    }

    public String getIddish() {
        return iddish;
    }

    public void setIddish(String iddish) {
        this.iddish = iddish;
    }

    public String getNamedish() {
        return namedish;
    }

    public void setNamedish(String namedish) {
        this.namedish = namedish;
    }
}
