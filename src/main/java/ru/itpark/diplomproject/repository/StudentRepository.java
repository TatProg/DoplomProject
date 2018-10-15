package ru.itpark.diplomproject.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itpark.diplomproject.domain.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StudentRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Student> findAllStudents() {
        return jdbcTemplate.query("SELECT id, secondName, firstName, lastName, groupNumber FROM students",
                new RowMapper<Student>() {
                    @Override
                    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new Student(
                                resultSet.getInt("id"),
                                resultSet.getString("secondName"),
                                resultSet.getString("firstName"),
                                resultSet.getString("lastName"),
                                resultSet.getInt("groupNumber")
                        );
                    }
                }
        );
    }
}
