package me.ilich.baneks.data;

import java.io.Serializable;

public class Anek implements Serializable {

    private final String title;
    private final String body;

    public Anek(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

}
