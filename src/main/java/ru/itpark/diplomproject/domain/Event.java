package ru.itpark.diplomproject.domain;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private final int id;
    private final String nameEvent;
    private final String description;
    private final List<Role> roles;

    public Event(int id, String nameEvent, String description, List<Role> roles) {
        this.id = id;
        this.nameEvent = nameEvent;
        this.description = description;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public String getDescription() {
        return description;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
