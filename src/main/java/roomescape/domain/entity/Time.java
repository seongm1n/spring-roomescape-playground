package roomescape.domain.entity;

import java.time.LocalTime;

public class Time {
    private final Long id;
    private final LocalTime time;

    public Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public static Time of(LocalTime time) {
        return new Time(null, time);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
