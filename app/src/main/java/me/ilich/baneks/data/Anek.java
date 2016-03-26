package me.ilich.baneks.data;

import java.io.Serializable;

public class Anek implements Serializable {

    private final String id;
    private final String title;
    private final String body;
    private final String rating;
    private final boolean rated;

    public Anek(String id, String title, String body, String rating, boolean rated) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.rating = rating;
        this.rated = rated;
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

    public boolean isRated() {
        return rated;
    }

    public String getId() {
        return id;
    }
}
