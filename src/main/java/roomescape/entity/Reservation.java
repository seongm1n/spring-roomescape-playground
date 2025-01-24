package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private Long id;
    private Person person;
    private LocalDate date;
    private Time time;

    public Reservation(Long id, Person person, LocalDate date, Time time) {
        this.id = id;
        this.person = person;
        this.date = date;
        this.time = time;
    }

    public Reservation(Person person, LocalDate date, Time time) {
        this.id = 0L;
        this.person = person;
        this.date = date;
        this.time = time;
    }

    public void validateReservationDate() {
        LocalDate today = LocalDate.now();
        if (today.isAfter(date)) {
            throw new IllegalArgumentException("시간이 지난 예약은 예약할 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public String getPersonName() {
        return person.getName();
    }

    public LocalDate getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public LocalTime getLocalTime() {
        return time.getTime();
    }

    public Long getTimeId() {
        return time.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
