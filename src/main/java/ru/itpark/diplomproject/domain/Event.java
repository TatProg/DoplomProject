package ru.itpark.diplomproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private int id;
    private String nameEvent;
    private String volonteerRoles;
    private String dateEvent;//TODO: DATE or STRING
}
