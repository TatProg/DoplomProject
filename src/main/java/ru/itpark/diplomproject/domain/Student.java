package ru.itpark.diplomproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int id;
    private String secondName;
    private String firstName;
    private String lastName;
    private int score;
}
