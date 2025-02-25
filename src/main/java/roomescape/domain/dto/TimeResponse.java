package roomescape.domain.dto;

import roomescape.domain.entity.Time;

import java.time.LocalTime;

public class TimeResponse {
    private final Long id;
    private final LocalTime time;

    public TimeResponse(Time time) {
        this.id = time.getId();
        this.time = time.getTime();
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
