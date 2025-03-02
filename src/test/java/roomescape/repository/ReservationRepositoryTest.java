package roomescape.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.entity.Reservation;

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
        jdbcTemplate.update("INSERT INTO TIME (ID, TIME) VALUES (?, ?)", 1L, "10:00");
        jdbcTemplate.update("INSERT INTO TIME (ID, TIME) VALUES (?, ?)", 2L, "11:00");
    }

    @Test
    void testFindAll() {
        Long timeId1 = 1L;
        Long timeId2 = 2L;
        reservationRepository.save("seongmin", LocalDate.of(2025, 10, 10), timeId1);
        reservationRepository.save("theo", LocalDate.of(2025, 10, 11), timeId2);

        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(2);
        assertThat(reservations.get(0).getName()).isEqualTo("seongmin");
        assertThat(reservations.get(0).getReservationDate()).isEqualTo(LocalDate.of(2025, 10, 10));
        assertThat(reservations.get(0).getReservationTime().getTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(reservations.get(1).getName()).isEqualTo("theo");
        assertThat(reservations.get(1).getReservationDate()).isEqualTo(LocalDate.of(2025, 10, 11));
        assertThat(reservations.get(1).getReservationTime().getTime()).isEqualTo(LocalTime.of(11, 0));
    }

    @Test
    void testSave() {
        Long timeId = 1L;
        Long id = reservationRepository.save("seongmin", LocalDate.of(2025, 10, 10), timeId);
        Assertions.assertAll(
                () -> assertThat(id).isNotNull(),
                () -> assertThat(reservationRepository.existsById(id)).isTrue()
        );
    }

    @Test
    void testDeleteById() {
        Long timeId = 1L;
        Long id = reservationRepository.save("seongmin", LocalDate.of(2025, 10, 10), timeId);
        assertThat(reservationRepository.existsById(id)).isTrue();
        reservationRepository.deleteById(id);
        assertThat(reservationRepository.existsById(id)).isFalse();
    }

    @Test
    void testExistsById() {
        Long timeId = 1L;
        Long id = reservationRepository.save("seongmin", LocalDate.of(2025, 10, 10), timeId);
        assertThat(reservationRepository.existsById(id)).isTrue();
        assertThat(reservationRepository.existsById(100L)).isFalse();
    }
}
