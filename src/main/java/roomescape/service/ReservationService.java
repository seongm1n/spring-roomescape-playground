package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.dto.ReservationRequest;
import roomescape.domain.dto.ReservationResponse;
import roomescape.domain.entity.Reservation;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;
import roomescape.valid.ReservationValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAll();
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        ReservationValidator.validate(request);

        Reservation reservation = new Reservation(
                index.incrementAndGet(),
                request.getName(),
                request.getDate(),
                request.getTime()
        );
        reservations.add(reservation);
        return ReservationResponse.from(reservation);
    }

    public void deleteReservation(long id) {
        Reservation reservation = reservations.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException("해당 예약을 찾을 수 없습니다."));

        reservations.remove(reservation);
    }
}
