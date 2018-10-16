package ru.itpark.diplomproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Student {
    private final int id;
    private final String secondName;
    private final String firstName;
    private final String lastName;
    private final int score;

    public Student(int id, String secondName, String firstName, String lastName, int score) {
        this.id = id;
        this.secondName = secondName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.score = score;
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
}
