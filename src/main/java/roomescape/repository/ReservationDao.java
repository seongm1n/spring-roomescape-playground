package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Person;
import roomescape.entity.Reservation;
import roomescape.entity.Time;
import roomescape.exception.EntityNotFoundException;
import roomescape.util.CustomDateTimeFormat;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> reservationTimeRowMapper = (rs, rowNum) ->
       new Reservation(
            rs.getLong(1),
            new Person(rs.getString(2)),
            LocalDate.parse(rs.getString(3), CustomDateTimeFormat.dateFormatter),
            new Time(rs.getLong(4), LocalTime.parse(rs.getString(5), CustomDateTimeFormat.timeFormatter))
    );
    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) ->
            new Reservation(
                    rs.getLong(1),
                    new Person(rs.getString(2)),
                    LocalDate.parse(rs.getString(3), CustomDateTimeFormat.dateFormatter),
                    new Time(rs.getLong(4))
            );

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation save(Reservation reservation, Long timeId) {
        String query = "INSERT INTO RESERVATION (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    query,
                    new String[]{"id"});
            ps.setString(1, reservation.getPersonName());
            ps.setString(2, reservation.getDate().format(CustomDateTimeFormat.dateFormatter));
            ps.setLong(3, timeId);
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new Reservation(
                id,
                reservation.getPerson(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public List<Reservation> findAll() {
        String query = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value FROM reservation as r inner join time as t on r.time_id = t.id";

        return jdbcTemplate.query(query, reservationTimeRowMapper);
    }

    public List<Reservation> findByDate(LocalDate date) {
        String query = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value FROM reservation as r inner join time as t on r.time_id = t.id where r.date = ?";
        return jdbcTemplate.query(query, reservationTimeRowMapper, date.format(CustomDateTimeFormat.dateFormatter));
    }

    public Reservation findById(Long id) {
        String query = "SELECT * FROM RESERVATION WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(query, reservationTimeRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("해당 예약은 존재하지 않습니다.");
        }
    }

    public void deleteById(Long id) {
        String query = "delete from RESERVATION where id = ?";
        int count = jdbcTemplate.update(query, id);

        if (count == 0) {
            throw new EntityNotFoundException("해당 id의 예약은 존재하지 않습니다");
        }
    }

    public List<Reservation> findByTimeId(Long timeId) {
        String query = "SELECT * FROM RESERVATION WHERE time_id = ?";
        return jdbcTemplate.query(query, reservationRowMapper, timeId);
    }

    public Optional<Reservation> findByDateAndTime(LocalDate date, Long timeId) {
        try {
            String query = "SELECT * FROM RESERVATION WHERE date = ? AND time_id = ?";

            return Optional.ofNullable(jdbcTemplate.queryForObject(query, reservationRowMapper, date, timeId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }
}
