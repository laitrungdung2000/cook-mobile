package com.ui.letcook.Activity;

public class Like {
    private String id, namedish;
    public Like(){};
    public Like(String id, String namedish) {
        this.id = id;
        this.namedish = namedish;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamedish() {
        return namedish;
    }

    public void setNamedish(String namedish) {
        this.namedish = namedish;
    }
}
