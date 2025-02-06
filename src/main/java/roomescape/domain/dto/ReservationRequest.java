package roomescape.domain.dto;

import roomescape.domain.entity.Time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationRequest {
    private final LocalDate date;
    private final String name;
    private final Time time;

    public ReservationRequest(LocalDate date, String name, String time) {
        this.date = date;
        this.name = name;
        this.time = new Time(null, LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
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
