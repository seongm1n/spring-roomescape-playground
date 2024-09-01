package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private Long timeId;
    private LocalTime time;


    public Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, Long timeId, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTimeId() { return timeId; }

    public LocalTime getTime() {
        return time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTimeId(Long timeId) { this.timeId = timeId; }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", timeId=" + timeId +
                ", time=" + time +
                '}';
    }
}
