package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;
import roomescape.dto.request.ReservationTimeRequestDto;
import roomescape.exception.ReservationTimeNotFoundException;
import roomescape.infrastructure.JdbcReservationTimeRepository;
import roomescape.service.dto.ReservationTimeDto;

@Transactional
@Service
public class ReservationTimeService {

  private final ReservationTimeRepository reservationTimeRepository;

  public ReservationTimeService(JdbcReservationTimeRepository reservationTimeRepository) {
    this.reservationTimeRepository = reservationTimeRepository;
  }

  @Transactional(readOnly = true)
  public List<ReservationTimeDto> findAll() {
    return reservationTimeRepository.findAll()
                                    .stream()
                                    .map(ReservationTimeDto::from)
                                    .toList();
  }

  public ReservationTimeDto save(final ReservationTimeRequestDto reservationTimeRequestDto) {
    ReservationTime newReservationTime = new ReservationTime(
        null,
        reservationTimeRequestDto.getTime()
    );
    ReservationTime savedReservationTime = reservationTimeRepository.save(newReservationTime);
    return ReservationTimeDto.from(savedReservationTime);
  }

  public void deleteById(final Long id) {
    if (!reservationTimeRepository.deleteById(id)) {
      throw new ReservationTimeNotFoundException();
    }
  }
}
