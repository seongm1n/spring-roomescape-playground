package roomescape.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.entity.Reservation;
import roomescape.domain.entity.Time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class ReservationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM reservation");
    }

    @Test
    void 예약을_저장하고_조회할_수_있다() {
        // given
        Reservation reservation = new Reservation(null, "브라운", LocalDate.of(2023, 8, 5), new Time(LocalTime.of(10, 0)));

        // when
        Long id = reservationRepository.save(reservation);
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        assertThat(id).isNotNull();
        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).getName()).isEqualTo("브라운");
    }

    @Test
    void 예약을_삭제할_수_있다() {
        // given
        Reservation reservation = new Reservation(null, "브라운", LocalDate.of(2023, 8, 5), new Time(LocalTime.of(10, 0)));
        Long id = reservationRepository.save(reservation);

        // when
        reservationRepository.deleteById(id);
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        assertThat(reservations).isEmpty();
    }
}
