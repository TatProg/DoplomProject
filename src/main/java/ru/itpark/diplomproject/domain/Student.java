package ru.itpark.diplomproject.domain;

public class Student {
    private final int id;
    private final String secondName;
    private final String firstName;
    private final String lastName;
    private final int score;
    private final boolean isAdmin;

    public Student(int id, String secondName, String firstName, String lastName, int score, boolean isAdmin) {
        this.id = id;
        this.secondName = secondName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.score = score;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getScore() {
        return score;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
