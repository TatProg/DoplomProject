package ru.itpark.diplomproject.controller;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping("/api/getAllEvents")
    public List<Student> getAllEvents() {
        try {
            List<Student> students = jdbcTemplate.query("SELECT id, secondName, firstName, lastName, groupNumber, score FROM students",
                    new RowMapper<Student>() {
                        @Override
                        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                            return new Student(
                                    resultSet.getInt("id"),
                                    resultSet.getString("secondName"),
                                    resultSet.getString("firstName"),
                                    resultSet.getString("lastName"),
                                    resultSet.getInt("score")
                            );
                        }
                    }
            );
            return students;
        }catch (IllegalArgumentException e){
            return null;
        }
    }

    @RequestMapping("api/participantStudent")
    public void ParticipantStudent(@RequestParam(value = "userId", required = true, defaultValue = "") int userId,
                                   @RequestParam(value = "eventId",required = true, defaultValue = "") int eventId,
                                   @RequestParam(value = "roleId",required = true, defaultValue = "") int roleId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("eventId", eventId);
        params.put("roleId", roleId);
        params.put("userId", userId);
        jdbcTemplate.update("INSERT INTO participants (eventId, roleId, userId)  VALUES (?,?,?)", params);
    }

    @RequestMapping("api/removeParticipantStudent")
    public void RemoveParticipantStudent(@RequestParam(value = "userId", required = true, defaultValue = "") int userId,
                                         @RequestParam(value = "eventId",required = true, defaultValue = "") int eventId,
                                         @RequestParam(value = "roleId",required = true, defaultValue = "") int roleId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("userId", userId);
        params.put("eventId", eventId);
        params.put("roleId", roleId);
        jdbcTemplate.update("DELETE FROM participants WHERE (userId=?, eventId=?, roleid=?)", params);
    }

}
