package me.ilich.baneks.data;

public class TopItem {

    private final String id;
    private final String title;

    public TopItem(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
