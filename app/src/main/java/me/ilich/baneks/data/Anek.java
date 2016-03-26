package me.ilich.baneks.data;

import java.io.Serializable;

public class Anek implements Serializable {

    private final String title;
    private final String body;
    private final String rating;

    public Anek(String title, String body, String rating) {
        this.title = title;
        this.body = body;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getRating() {
        return rating;
    }
}
