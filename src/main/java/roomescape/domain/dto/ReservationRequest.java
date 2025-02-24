package roomescape.domain.dto;

import roomescape.domain.entity.Reservation;
import roomescape.domain.entity.Time;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    private final LocalDate date;
    private final String name;
    private final Time time;

    public ReservationRequest(LocalDate date, String name, LocalTime time) {
        this.date = date;
        this.name = name;
        this.time = new Time(null, time);
    }

    public Reservation toEntity() {
        return new Reservation(null, name, date, time);
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Time getTime() {
        return time;
    }
}
