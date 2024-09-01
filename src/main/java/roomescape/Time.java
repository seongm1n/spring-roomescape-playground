package roomescape;

import java.time.LocalTime;

public class Time {
    private Long id;
    private LocalTime time;

    public Time() {
    }

    public Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Time{" +
                "id=" + id +
                ", time=" + time +
                '}';
    }
}