package roomescape.domain.dto;

import java.time.LocalTime;

public class TimeResponse {
    private final Long id;
    private final LocalTime time;

    public TimeResponse(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
