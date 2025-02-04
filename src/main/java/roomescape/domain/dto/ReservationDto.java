package roomescape.domain.dto;

import roomescape.domain.entity.Reservation;

public class ReservationDto {
    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public ReservationDto() {
        this(null, null, null, null);
    }

    public ReservationDto(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationDto from(Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
