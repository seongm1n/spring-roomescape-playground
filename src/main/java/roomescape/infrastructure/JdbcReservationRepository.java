package roomescape.infrastructure;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTime;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

  private static final int NO_ROWS_AFFECTED = 0;

  private final JdbcTemplate jdbcTemplate;

  public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
      resultSet.getLong("reservation_id"),
      resultSet.getString("name"),
      resultSet.getDate("date").toLocalDate(),
      new ReservationTime(resultSet.getLong("time_id"),
          resultSet.getTime("time_value").toLocalTime())
  );

  @Override
  public List<Reservation> findAll() {
    final String sql =
        """
        select r.id as reservation_id, 
        r.name, 
        r.date,
        t.id as time_id, 
        t.time as time_value 
        from reservation as r 
        inner join time as t 
        on r.time_id = t.id
        """;
    return jdbcTemplate.query(sql, reservationRowMapper);
  }

  @Override
  public Reservation save(final Reservation reservation) {
    final String reservationSql = "insert into reservation (name, date, time_id) "
        + "values (?, ?, ?)";
    KeyHolder reservationKeyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement preparedStatement = connection.prepareStatement(reservationSql, new String[]{"id"});
      preparedStatement.setString(1, reservation.getName());
      preparedStatement.setString(2, reservation.getDate().toString());
      preparedStatement.setLong(3, reservation.getTime().getId());
      return preparedStatement;
    }, reservationKeyHolder);

    Long generatedId = reservationKeyHolder.getKey().longValue();
    
    return Reservation.of(generatedId, reservation);
  }

  @Override
  public boolean deleteById(Long id) {
    final String sql = "delete from reservation where id = ?";
    return jdbcTemplate.update(sql, id) > NO_ROWS_AFFECTED;
  }
}
