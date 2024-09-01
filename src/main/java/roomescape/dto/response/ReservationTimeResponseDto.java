package roomescape.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.service.dto.ReservationTimeDto;

public class ReservationTimeResponseDto {

  private final Long id;

  @JsonFormat(pattern = "HH:mm")
  private final LocalTime time;

  public ReservationTimeResponseDto(Long id, LocalTime time) {
    this.id = id;
    this.time = time;
  }

  public static ReservationTimeResponseDto from(final ReservationTimeDto reservationTimeDto) {
    return new ReservationTimeResponseDto(
        reservationTimeDto.getId(),
        reservationTimeDto.getTime());
  }

  public Long getId() {
    return id;
  }

  public LocalTime getTime() {
    return time;
  }
}
