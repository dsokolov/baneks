package me.ilich.baneks.data;

import java.io.Serializable;

public class TopItem implements Serializable {

    private final String id;
    private final String pos;
    private final String title;

    public TopItem(String id, String pos, String title) {
        this.id = id;
        this.pos = pos;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getPos() {
        return pos;
    }

    public String getTitle() {
        return title;
    }

}
