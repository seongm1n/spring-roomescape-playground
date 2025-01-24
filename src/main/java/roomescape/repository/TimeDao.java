package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Time;
import roomescape.exception.EntityAlreadyExistException;
import roomescape.exception.EntityNotFoundException;
import roomescape.util.CustomDateTimeFormat;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;

@Repository
public class TimeDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<roomescape.entity.Time> timeRowMapper = (rs, rowNum) -> new roomescape.entity.Time(
            rs.getLong("id"),
            LocalTime.parse(rs.getString("time"), CustomDateTimeFormat.timeFormatter)
    );

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> findAll() {
        String query = "SELECT * FROM time";

        return jdbcTemplate.query(query, timeRowMapper);
    }

    public Time save(Time reservationTime) {
        try {
            String query = "INSERT INTO time (time) VALUES (?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        query,
                        new String[]{"id"});
                ps.setString(1, reservationTime.getTime().format(CustomDateTimeFormat.timeFormatter));
                return ps;
            }, keyHolder);

            Long id = keyHolder.getKey().longValue();

            return new Time(
                    id,
                    reservationTime.getTime()
            );
        } catch (DuplicateKeyException e) {
            throw new EntityAlreadyExistException(e.getMessage());
        }
    }

    public void delete(Long id) {
        String query = "DELETE FROM time WHERE id = ?";
        int count = jdbcTemplate.update(query, id);

        if (count == 0) {
            throw new EntityNotFoundException("해당 id의 시간은 존재하지 않습니다");
        }
    }

    public Time findById(Long id) {
        try {
            String query = "SELECT * FROM time WHERE id = ?";
            return jdbcTemplate.queryForObject(query, timeRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("해당 시간은 존재하지 않습니다.");
        }
    }
}
