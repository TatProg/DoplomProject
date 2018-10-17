package ru.itpark.diplomproject.controller;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itpark.diplomproject.domain.Event;
import ru.itpark.diplomproject.domain.Role;
import ru.itpark.diplomproject.domain.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StudentController(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping("/api/getAllStudents")//Студент
    public List<Student> getAllStudents() {
        List<Student> students = jdbcTemplate.query("SELECT studentId, secondName, firstName, lastName, groupNumber, score FROM students",
                new RowMapper<Student>() {
                    @Override
                    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new Student(
                                resultSet.getInt("studentId"),
                                resultSet.getString("secondName"),
                                resultSet.getString("firstName"),
                                resultSet.getString("lastName"),
                                resultSet.getInt("score"),
                                false);
                    }
                }
        );
        return students;
    }

    @RequestMapping("api/participantStudent")//Студент
    public void ParticipantStudent(@RequestParam(value = "studentId", required = true, defaultValue = "") int studentId,
                                   @RequestParam(value = "eventId", required = true, defaultValue = "") int eventId,
                                   @RequestParam(value = "roleId", required = true, defaultValue = "") int roleId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("eventId", eventId);
        params.put("roleId", roleId);
        params.put("studentId", studentId);
        //System.out.println(eventId + " " + roleId + " " + studentId);
        jdbcTemplate.update("INSERT INTO participants (eventId, roleId, studentId)  VALUES ( :eventId, :roleId, :studentId)", params);
    }

    @RequestMapping("api/getParticipants")//Студент
    public List<Student> Participants(@RequestParam(value = "eventId", required = true, defaultValue = "") int eventId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("eventId", eventId);

        //System.out.println(eventId + " " + roleId + " " + studentId);
        List<Student> students = jdbcTemplate.query("SELECT * FROM students" +
                        " WHERE studentId IN (SELECT studentId FROM participants WHERE eventId=:eventId)",
                params,
                new RowMapper<Student>() {
                    @Override
                    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                        boolean isAdmin = false;

                        return new Student(
                                resultSet.getInt("studentId"),
                                resultSet.getString("secondName"),
                                resultSet.getString("firstName"),
                                resultSet.getString("lastName"),
                                resultSet.getInt("score"),
                                false
                        );
                    }
                });
        return  students;

    }


    @RequestMapping("api/removeParticipantStudent")//Студент
    public void RemoveParticipantStudent(@RequestParam(value = "studentId", required = true, defaultValue = "") int studentId,
                                         @RequestParam(value = "eventId", required = true, defaultValue = "") int eventId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("studentId", studentId);
        params.put("eventId", eventId);
        //params.put("roleId", roleId);
        jdbcTemplate.update("DELETE FROM participants WHERE studentId =:studentId AND eventId = :eventId", params);
    }

    @RequestMapping("api/auth")
    public Student Auth(@RequestParam(value = "username", required = true) String username,
                        @RequestParam(value = "password", required = true) String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        List<Student> students = jdbcTemplate.query("SELECT * FROM students" +
                        " WHERE username = :username AND password = :password",
                params,
                new RowMapper<Student>() {
                    @Override
                    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                        boolean isAdmin = false;
                        try {
                           isAdmin = jdbcTemplate.query("SELECT * FROM moderators" +
                                            " WHERE studentId = " + resultSet.getInt("studentId"),
                                    new RowMapper<Boolean>() {
                                        @Override
                                        public Boolean mapRow(ResultSet resultSet, int i) throws SQLException {

                                            return true;
                                        }

                                    }).isEmpty();
                           isAdmin = !isAdmin;
                        }catch (SQLException e){
                            isAdmin = false;
                        }
                        return new Student(
                                resultSet.getInt("studentId"),
                                resultSet.getString("secondName"),
                                resultSet.getString("firstName"),
                                resultSet.getString("lastName"),
                                resultSet.getInt("score"),
                                isAdmin
                                );
                    }
                });
        if(students.isEmpty())
            return null;
        return students.get(0);//из листа выводим самую первую запись
    }


    @RequestMapping("api/getStudentEventList")//Студент
    public List<Event> getStudentEventList(@RequestParam(value = "studentId", required = true, defaultValue = "") int studentId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("studentId", studentId);
       // List<Role> oneRole =

        List<Event> studentEvents = jdbcTemplate.query(
                "SELECT * FROM  events  WHERE eventId  IN " +
                        "(SELECT eventId FROM participants WHERE studentId = " + studentId + ")",
                //SELECT nameEvent, dateEvent FROM events WHERE success=1 AND " +
                //                "SELECT roleName, score, studentId FROM eventRoles WHERE student id ="+studentId,
                new RowMapper<Event>() {
                    @Override
                    public Event mapRow(ResultSet resultSet, int i) throws SQLException {
                        params.put("eventId",resultSet.getInt("eventId"));
                        return new Event(
                                resultSet.getInt("eventId"),
                                resultSet.getString("nameEvent"),
                                resultSet.getString("description"),
                                jdbcTemplate.query(
                                        "SELECT * FROM eventRoles WHERE eventId=:eventId AND eventRoleId IN " +
                                                "(SELECT roleId FROM participants WHERE studentId=:studentId)", params,
                                        new RowMapper<Role>() {
                                            @Override
                                            public Role mapRow(ResultSet resultSet, int i) throws SQLException {
                                                return new Role(
                                                        resultSet.getInt("eventId"),
                                                        resultSet.getString("roleName"),
                                                        resultSet.getInt("quantity")
                                                );
                                            }
                                        }),
                                resultSet.getString("dateEvent"));
                    }
                }
        );
        return studentEvents;
    }

    @RequestMapping("api/removeStudentById")//Модератор
    public void removeStudentById(@RequestParam(value = "studentId", required = true, defaultValue = "") int studentId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("studentId", studentId);
        jdbcTemplate.update("DELETE FROM students AND participants WHERE studentId=:studentId", params);
    }

}
