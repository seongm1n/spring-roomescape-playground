package roomescape.exception;

public class ReservationNotFoundException extends RuntimeException{

  public ReservationNotFoundException() {
    super("[ERROR] 예약이 존재하지 않습니다.");
  }
}
