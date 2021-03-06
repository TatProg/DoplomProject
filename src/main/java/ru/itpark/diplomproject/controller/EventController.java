package ru.itpark.diplomproject.controller;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itpark.diplomproject.domain.Event;
import ru.itpark.diplomproject.domain.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EventController {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EventController(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Role> getAllRoles(int eventId) {
        List<Role> roles = jdbcTemplate.query("SELECT eventRoleId, eventId, roleName, quantity FROM eventRoles WHERE eventId=" + eventId,
                new RowMapper<Role>() {
                    @Override
                    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new Role(
                                resultSet.getInt("eventId"),
                                resultSet.getString("roleName"),
                                resultSet.getInt("quantity")
                        );
                    }
                }
        );

        return roles;
    }

    @RequestMapping("/api/getAllEvents")//Студент
    public List<Event> getAllEvents() {
        List<Event> events = jdbcTemplate.query("SELECT eventId, nameEvent, description, dateEvent FROM events",
                new RowMapper<Event>() {
                    @Override
                    public Event mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new Event(
                                resultSet.getInt("eventId"),
                                resultSet.getString("nameEvent"),
                                resultSet.getString("description"),
                                getAllRoles(resultSet.getInt("eventId")),
                                resultSet.getString("dateEvent"));
                    }
                }
        );

        return events;

    }

    @RequestMapping(value = "api/addEvent")//Модератор
    public void addEvent(@RequestParam(value = "title", required = true, defaultValue = "") String title,
                         @RequestParam(value = "description", required = true, defaultValue = "") String description
                         //sql date
                        // @RequestParam(value = "roles", required = true, defaultValue = "") Array roles
                         ) {
        Map<String, Object> params = new HashMap<>();
        params.put("nameEvent", title);
        params.put("description", description);
        params.put("date", "17.08.2018");
        jdbcTemplate.update("INSERT INTO events (nameEvent, description) VALUES (:nameEvent, :description)", params);
/*
        List<Integer> lastIds = jdbcTemplate.query("SELECT * FROM events ORDER BY eventId DESC limit 1",
                new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getInt("eventId");

                    }
                }
        );
        int eventId = lastIds.get(0);

        Map<String, Object> roleParams = new HashMap<>();

        roleParams.put("eventId", eventId);
/*
        for (Role role : roles) {
            roleParams.put("roleName", role.getName());
            roleParams.put("quantity", role.getCount());
            jdbcTemplate.update("INSERT INTO eventRoles (eventId, roleName, quantity) VALUES (:eventId, :roleName, :quantity)", roleParams);
        }*/
       // return "Success";
    }

    @RequestMapping("api/removeEventById")//Модератор
    public void removeEventById(@RequestParam(value = "eventId", required = true, defaultValue = "") int eventId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("eventId", eventId);
        jdbcTemplate.update("DELETE FROM events WHERE eventId=:eventId", params);
    }

    @RequestMapping("api/successEvent")//Модератор
    public void successEvent(@RequestParam(value = "eventId", required = true, defaultValue = "") int eventId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("eventId", eventId);
        jdbcTemplate.update("UPDATE events SET success=1 WHERE eventId=:eventId", params);
        List<Role> roles = jdbcTemplate.query("SELECT * FROM eventRoles WHERE eventId =:eventId", params,
                new RowMapper<Role>() {
                    @Override
                    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new Role(resultSet.getInt("eventRoleId"), resultSet.getString("roleName"),
                                resultSet.getInt("score"));
                    }
                });
        for (Role role : roles) {
            params.put("eventId", eventId);
            params.put("roleId", role.getId());
            jdbcTemplate.update("UPDATE students SET score=score+" + role.getCount() + " WHERE studentId  IN " +               //Возможно потребуется запись ввида WHERE id AS userId IN
                    "(SELECT studentId FROM participants WHERE eventId=:eventId AND roleId=:roleId)", params);

        }
    }

    @RequestMapping("api/changeStudentById")//Модератор
    public void changeStudentInEventById(@RequestParam(value = "firstStudent", required = true, defaultValue = "") int firstStudent,
                                         @RequestParam(value = "secondStudent", required = true, defaultValue = "") int secondStudent,
                                         @RequestParam(value = "eventId", required = true, defaultValue = "") int eventId) {
        Map<String, Integer> paramsDelete = new HashMap<>();
        paramsDelete.put("eventId", eventId);
        paramsDelete.put("studentId", firstStudent);
        jdbcTemplate.update("DELETE FROM participants WHERE (eventId=:eventId, studentId=:studentId)", paramsDelete);
        Map<String, Integer> paramsPaste = new HashMap<>();
        paramsPaste.put("eventId", eventId);
        paramsPaste.put("studentId", secondStudent);
        jdbcTemplate.update("UPDATE INTO participants WHERE (eventId=:eventId, studentId=:studentId)", paramsPaste);

    }
/*
    @RequestMapping("/")
    public String index(){
        return "redirect:/static/index.html";
    }
*/
}