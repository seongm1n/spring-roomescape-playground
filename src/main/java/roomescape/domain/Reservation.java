package roomescape.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

  private final Long id;

  private final String name;

  private final LocalDate date;

  private final ReservationTime time;

  public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
    validateDate(date);
    this.id = id;
    this.name = name;
    this.date = date;
    this.time = time;
  }

  public static Reservation of(Long id, Reservation reservation) {
    return new Reservation(id, reservation.name, reservation.date, reservation.time);
  }

  private void validateDate(LocalDate date) {
    if (date.isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("[ERROR] 과거의 예약은 생성할 수 없습니다.");
    }
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public LocalDate getDate() {
    return date;
  }

  public ReservationTime getTime() {
    return time;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Reservation reservation)) {
      return false;
    }
    return Objects.equals(id, reservation.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
