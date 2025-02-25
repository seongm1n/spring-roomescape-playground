package roomescape.domain.dto;

import roomescape.domain.entity.Reservation;

import java.time.LocalDate;

public class ReservationResponse {
    private final long id;
    private final String name;
    private final LocalDate date;
    private final TimeResponse time;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getReservationDate();
        this.time = new TimeResponse(reservation.getReservationTime());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public TimeResponse getTime() {
        return time;
    }
}
