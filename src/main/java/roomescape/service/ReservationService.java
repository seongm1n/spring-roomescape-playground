package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.dto.ReservationRequest;
import roomescape.domain.dto.ReservationResponse;
import roomescape.domain.entity.Reservation;
import roomescape.exception.InvalidReservationRequestException;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse save(ReservationRequest request) {
        Reservation reservation = request.toEntity();
        Long id = reservationRepository.save(reservation);
        return new ReservationResponse(id, reservation.getName(), reservation.getReservationDate(), reservation.getReservationTime());
    }

    public void deleteById(Long id) {
        if (id == null || !reservationRepository.existsById(id)) {
            throw new InvalidReservationRequestException("이미 삭제된 예약입니다.");
        }
        reservationRepository.deleteById(id);
    }
}
