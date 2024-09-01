package roomescape.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReservationRequestDto {

  @NotBlank(message = "[ERROR] 이름이 비어 있습니다.")
  private final String name;

  @NotNull(message = "[ERROR] 날짜가 비어 있습니다.")
  private final LocalDate date;

  @NotNull(message = "[ERROR] 예약 시간을 선택해야합니다.")
  private final Long timeId;

  public ReservationRequestDto(String name, LocalDate date, Long time) {
    this.name = name;
    this.date = date;
    this.timeId = time;
  }

  public String getName() {
    return name;
  }

  public LocalDate getDate() {
    return date;
  }

  public Long getTimeId() {
    return timeId;
  }
}
