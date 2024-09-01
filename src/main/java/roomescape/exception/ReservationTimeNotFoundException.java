package roomescape.exception;

public class ReservationTimeNotFoundException extends RuntimeException{

  public ReservationTimeNotFoundException() {
    super("[ERROR] 예약 시간이 존재하지 않습니다.");
  }
}
