package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.entity.Reservation;

import java.time.LocalDate;
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
    }

    @Test
    void testFindAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(0);
    }

    @Test
    void testSave() {
        Long timeId = 1L;
        Long id = reservationRepository.save("seongmin", LocalDate.of(2025, 10, 10), timeId);
        assertThat(id).isNotNull();
        assertThat(reservationRepository.existsById(id)).isTrue();
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
