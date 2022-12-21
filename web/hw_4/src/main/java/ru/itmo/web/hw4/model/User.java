package ru.itmo.web.hw4.model;

public class User {
    private final long id;
    private final String handle;
    private final String name;
    private long totalPosts;
    private final Color color;

    public User(long id, String handle, String name, long totalPosts, Color color) {
        this.id = id;
        this.handle = handle;
        this.name = name;
        this.totalPosts = totalPosts;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setTotalPosts(long num) {
        totalPosts = num;
    }

    public long getTotalPosts() {
        return totalPosts;
    }
    public long getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getName() {
        return name;
    }
}
