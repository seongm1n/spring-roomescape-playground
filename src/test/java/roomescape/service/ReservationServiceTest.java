package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.dto.ReservationRequest;
import roomescape.domain.dto.ReservationResponse;
import roomescape.domain.entity.Reservation;
import roomescape.domain.entity.Time;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private final Reservation reservation = new Reservation(1L, "브라운", LocalDate.of(2025, 8, 5), new Time(null, LocalTime.of(10, 0)));
    private final ReservationRequest request = new ReservationRequest(LocalDate.of(2025, 8, 5), "브라운", LocalTime.of(10, 0));

    @Test
    void 모든_예약_조회() {
        // given
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));

        // when
        List<ReservationResponse> reservations = reservationService.findAll();

        // then
        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).getName()).isEqualTo("브라운");
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void 예약_저장() {
        // given
        when(reservationRepository.save(any(Reservation.class))).thenReturn(1L);

        // when
        ReservationResponse response = reservationService.save(request);

        // then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("브라운");
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void 예약_삭제() {
        // given
        when(reservationRepository.existsById(1L)).thenReturn(true);
        doNothing().when(reservationRepository).deleteById(1L);

        // when
        reservationService.deleteById(1L);

        // then
        verify(reservationRepository, times(1)).deleteById(1L);
    }
}
