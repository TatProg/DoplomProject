package ru.itpark.diplomproject.controller;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itpark.diplomproject.domain.Event;
import ru.itpark.diplomproject.domain.Role;

import java.sql.Date;
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

    public List<Role> getAllRoles(int idEvent) {
        List<Role> roles = jdbcTemplate.query("SELECT id, eventId, roleName, quantity FROM eventRoles WHERE eventId=" + idEvent,
                new RowMapper<Role>() {
                    @Override
                    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new Role(
                                resultSet.getInt("id"),
                                resultSet.getString("roleName"),
                                resultSet.getInt("quantity")
                        );
                    }
                }
        );

        return roles;
    }

    @RequestMapping("/api/getEvents")
    public List<Event> getAllEvents() {
        List<Event> events = jdbcTemplate.query("SELECT id, nameEvent, description, dateEvent FROM events",
                new RowMapper<Event>() {
                    @Override
                    public Event mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new Event(
                                resultSet.getInt("id"),
                                resultSet.getString("nameEvent"),
                                resultSet.getString("description"),
                                getAllRoles(resultSet.getInt("id"))
                        );
                    }
                }
        );

        return events;

    }

    @RequestMapping("api/addEvent")
    public void addEvent(@RequestParam(value = "title", required = true,defaultValue = "") String title,
                         @RequestParam(value = "description", required = true,defaultValue = "") String description,
                         @RequestParam(value = "date", required = true,defaultValue = "")Date date,//sql date
                         @RequestParam(value = "roles", required = true,defaultValue = "") List<Role> roles) {
        Map<String,Object> params = new HashMap<>();
        params.put("nameEvent", title);
        params.put("description", description);
        params.put("date", date);
        jdbcTemplate.update("INSERT INTO events (nameEvent, description, date) VALUES (?,?,?)", params);

        List<Integer> lastIds  = jdbcTemplate.query("SELECT * FROM events ORDER BY id DESC limit 1",
                new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getInt("id");

                    }
                }
        );
        int eventId = lastIds.get(0);

        Map<String,Object> roleParams = new HashMap<>();

        roleParams.put("eventId", eventId);

        for (Role role : roles) {
            roleParams.put("name", role.getName());
            roleParams.put("quantity",role.getCount());
            jdbcTemplate.update("INSERT INTO eventRoles (eventId, name, quantity) VALUES (?,?,?)", roleParams);
        }

    }

    @RequestMapping("api/removeEventById")
    public void removeEventById(@RequestParam(value = "eventId", required = true, defaultValue = "") int eventId){
        Map<String, Integer> params = new HashMap<>();
        params.put("eventId", eventId);
        jdbcTemplate.update("DELETE FROM events WHERE eventId=?", params);
    }

    //TODO где будет логика начисления баллов
    @RequestMapping("api/successEvent")
    public void successEvent(@RequestParam(value = "eventId", required = true, defaultValue = "") int eventId){
        Map<String, Integer> params = new HashMap<>();
        params.put("eventId", eventId);
        jdbcTemplate.update("UPDATE INTO events (success=1) WHERE eventId=?", params);
        //jdbcTemplate.query("SELECT INTO participants ")
    }

    @RequestMapping("api/changeStudentById")
    public void changeStudentInEventById(@RequestParam(value = "firstStudent", required = true, defaultValue = "") int firstStudent,
                                         @RequestParam(value = "secondStudent", required = true, defaultValue = "") int secondStudent,
                                         @RequestParam(value = "eventId", required = true, defaultValue = "") int eventId){
        Map<String, Integer> paramsDelete = new HashMap<>();
        paramsDelete.put("eventId", eventId);
        paramsDelete.put("id", firstStudent);
        jdbcTemplate.update("DELETE FROM events WHERE (eventId=?, id=?)", paramsDelete);
        Map<String, Integer> paramsPaste = new HashMap<>();
        paramsPaste.put("eventId", eventId);
        paramsPaste.put("id", secondStudent);
        jdbcTemplate.update("UPDATE INTO events WHERE (eventId=?, id=?)", paramsPaste);

    }

}
//org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'eventController' defined in file [/Users/Aydar/IdeaProjects/DoplomProject/target/classes/ru/itpark/diplomproject/controller/EventController.class]: Unsatisfied dependency expressed through constructor parameter 0; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'namedParameterJdbcTemplate' defined in class path resource [org/springframework/boot/autoconfigure/jdbc/JdbcTemplateAutoConfiguration$NamedParameterJdbcTemplateConfiguration.class]: Unsatisfied dependency expressed through method 'namedParameterJdbcTemplate' parameter 0; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration$JdbcTemplateConfiguration': Unsatisfied dependency expressed through constructor parameter 0; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource' defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class]: Initialization of bean failed; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'org.springframework.boot.autoconfigure.jdbc.DataSourceInitializerInvoker': Invocation of init method failed; nested exception is org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException: Property spring.datasource.schema with value 'class path resource [SQL/schema-h2.sql]' is invalid: The specified resource does not exist.

//org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'eventController' defined in file [/Users/Aydar/IdeaProjects/DoplomProject/target/classes/ru/itpark/diplomproject/controller/EventController.class]: Unsatisfied dependency expressed through constructor parameter 0; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'namedParameterJdbcTemplate' defined in class path resource [org/springframework/boot/autoconfigure/jdbc/JdbcTemplateAutoConfiguration$NamedParameterJdbcTemplateConfiguration.class]: Unsatisfied dependency expressed through method 'namedParameterJdbcTemplate' parameter 0; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration$JdbcTemplateConfiguration': Unsatisfied dependency expressed through constructor parameter 0; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource' defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class]: Initialization of bean failed; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'org.springframework.boot.autoconfigure.jdbc.DataSourceInitializerInvoker': Invocation of init method failed; nested exception is org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException: Property spring.datasource.schema with value 'class path resource [SQL/schema-h2.sql]' is invalid: The specified resource does not exist.
