package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.Time;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TimeDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;


    public TimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAll() {
        return jdbcTemplate.query("SELECT * FROM time", new TimeRowMapper());
    }

    public Time insert(Time time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", time.getTime());

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
        time.setId(id.longValue());
        return time;
    }

    public boolean delete(Long id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
        return rowsAffected > 0;
    }

    private static class TimeRowMapper implements RowMapper<Time> {
        @Override
        public Time mapRow(ResultSet rs, int rowNum) throws SQLException {
            Time time = new Time();
            time.setId(rs.getLong("id"));
            time.setTime(rs.getTime("time").toLocalTime());
            return time;
        }
    }
}
