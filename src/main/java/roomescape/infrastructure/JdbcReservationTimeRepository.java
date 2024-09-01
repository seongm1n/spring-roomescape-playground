package roomescape.infrastructure;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;
import roomescape.exception.ReservationTimeNotFoundException;

@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

  private static final int NO_ROWS_AFFECTED = 0;

  private final JdbcTemplate jdbcTemplate;

  public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private final RowMapper<ReservationTime> reservationTimeRowMapper = (rs, rowNum) -> new ReservationTime(
      rs.getLong("id"),
      rs.getTime("time").toLocalTime()
  );

  @Override
  public List<ReservationTime> findAll() {
    final String sql = "select id, time from time";
    return jdbcTemplate.query(sql, reservationTimeRowMapper);
  }

  @Override
  public ReservationTime findById(Long id) {
    final String sql = "select id, time from time where id = ?";
    return jdbcTemplate.query(sql, reservationTimeRowMapper, id)
                       .stream()
                       .findAny()
                       .orElseThrow(ReservationTimeNotFoundException::new);
  }

  @Override
  public ReservationTime save(final ReservationTime reservationTime) {
    final String sql = "insert into time (time) values (?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[] {"id"});
      preparedStatement.setString(1, reservationTime.getTime().toString());
      return preparedStatement;
    }, keyHolder);
    return new ReservationTime(keyHolder.getKey().longValue(), reservationTime.getTime());
  }

  @Override
  public boolean deleteById(final Long id) {
    final String sql = "delete from time where id = ?";
    return jdbcTemplate.update(sql, id) > NO_ROWS_AFFECTED;
  }
}
