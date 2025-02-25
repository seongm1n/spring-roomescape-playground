package roomescape.domain.dto;

import java.time.LocalTime;

public class TimeRequest {
    private LocalTime time;

    public TimeRequest(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
