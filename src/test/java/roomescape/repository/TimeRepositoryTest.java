package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.entity.Time;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class TimeRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private TimeRepository timeRepository;

    @BeforeEach
    void setUp() {
        timeRepository = new TimeRepository(jdbcTemplate);
    }

    @Test
    void testFindAll() {
        jdbcTemplate.update("INSERT INTO TIME (ID, TIME) VALUES (?, ?)", 1L, "10:00");
        List<Time> times = timeRepository.findAll();
        assertThat(times.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void testSave() {
        Time time = new Time(null, LocalTime.of(10, 0));
        Long id = timeRepository.save(time);
        assertThat(id).isNotNull();
        Time savedTime = timeRepository.findById(id);
        assertThat(savedTime.getId()).isEqualTo(id);
    }

    @Test
    void testDeleteById() {
        jdbcTemplate.update("INSERT INTO TIME (ID, TIME) VALUES (?, ?)", 1L, "10:00");
        timeRepository.deleteById(1L);
        List<Time> times = timeRepository.findAll();
        assertThat(times).isEmpty();
    }

    @Test
    void testFindById() {
        jdbcTemplate.update("INSERT INTO time (id, time) VALUES (?, ?)", 1L, "10:00");
        Time time = timeRepository.findById(1L);
        assertThat(time).isNotNull();
        assertThat(time.getTime()).isEqualTo(LocalTime.of(10, 0));
    }
}
