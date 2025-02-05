package roomescape.domain.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate reservationDate;
    private final Time reservationTime;

    public Reservation(Long id, String name, LocalDate reservationDate, Time reservationTime) {
        this.id = id;
        this.name = name;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return reservationDate;
    }

    public Time getTime() {
        return reservationTime;
    }
}
