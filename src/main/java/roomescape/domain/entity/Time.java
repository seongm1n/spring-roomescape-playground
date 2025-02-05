package roomescape.domain.entity;

import java.time.LocalTime;

public class Time {
    private final Long id;
    private final LocalTime value;

    public Time(Long id, LocalTime value) {
        this.id = id;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getValue() {
        return value;
    }
}
