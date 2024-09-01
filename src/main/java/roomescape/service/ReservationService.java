package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTimeRepository;
import roomescape.dto.request.ReservationRequestDto;
import roomescape.exception.ReservationNotFoundException;
import roomescape.service.dto.ReservationDto;

@Transactional
@Service
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final ReservationTimeRepository reservationTimeRepository;

  public ReservationService(ReservationRepository reservationRepository,
      ReservationTimeRepository reservationTimeRepository) {
    this.reservationRepository = reservationRepository;
    this.reservationTimeRepository = reservationTimeRepository;
  }

  @Transactional(readOnly = true)
  public List<ReservationDto> findAll() {
    return reservationRepository.findAll().stream()
                                .map(ReservationDto::from)
                                .toList();
  }

  public ReservationDto save(final ReservationRequestDto reservationRequestDto) {
    Reservation newReservation = new Reservation(
        null,
        reservationRequestDto.getName(),
        reservationRequestDto.getDate(),
        reservationTimeRepository.findById(reservationRequestDto.getTimeId())
    );
    Reservation savedReservation = reservationRepository.save(newReservation);
    return ReservationDto.from(savedReservation);
  }

  public void deleteById(Long id) {
    if (!reservationRepository.deleteById(id)) {
      throw new ReservationNotFoundException();
    }
  }
}
