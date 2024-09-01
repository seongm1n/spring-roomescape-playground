package roomescape.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public class ReservationTimeRequestDto {

  @NotNull(message = "[ERROR] 시간이 비어 있습니다.")
  private final LocalTime time;

  @JsonCreator // 단일 인자를 받을 경우 명시가 필요
  public ReservationTimeRequestDto(LocalTime time) {
    this.time = time;
  }

  public LocalTime getTime() {
    return time;
  }
}
