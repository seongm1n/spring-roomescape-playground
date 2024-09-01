package roomescape.service.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public class ReservationTimeDto {

  private final Long id;
  private final LocalTime time;

  public ReservationTimeDto(Long id, LocalTime time) {
    this.id = id;
    this.time = time;
  }

  public static ReservationTimeDto from(final ReservationTime reservationTime) {
    return new ReservationTimeDto(
        reservationTime.getId(),
        reservationTime.getTime()
    );
  }

  public Long getId() {
    return id;
  }

  public LocalTime getTime() {
    return time;
  }
}
