package ru.itpark.diplomproject.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itpark.diplomproject.domain.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EventRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EventRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Event> findAllEvents() {//вывод для общей страницы?
        return jdbcTemplate.query("SELECT id, nameEvent, volonteerRoles, dateEvent FROM events",
                new RowMapper<Event>() {
                    @Override
                    public Event mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new Event(
                                resultSet.getInt("id"),
                                resultSet.getString("nameEvent"),
                                resultSet.getString("volonteerRoles"),
                                resultSet.getString("dateEvent")
                        );
                    }
                }
        );
    }

}
