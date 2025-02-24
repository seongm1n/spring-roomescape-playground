package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.entity.Time;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, this::mapRowToTime);
    }

    public Long save(Time time) {
        Map<String, Object> params = new HashMap<>();
        params.put("time", time.getTime().toString());

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Time mapRowToTime(ResultSet rs, int rowNum) throws SQLException {
        return new Time(
                rs.getLong("id"),
                rs.getObject("time", LocalTime.class)
        );
    }
}
