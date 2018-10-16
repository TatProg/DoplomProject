package ru.itpark.diplomproject.domain;

public class Role {
    private final int id;
    private final String name;
    private final int count;

    public Role(int id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
