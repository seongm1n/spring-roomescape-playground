package roomescape.domain.dto;

import java.time.LocalDate;

public class ReservationRequest {
    private LocalDate date;
    private String name;
    private Long time;

    public ReservationRequest(LocalDate date, String name, Long time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Long getTime() {
        return time;
    }
}
