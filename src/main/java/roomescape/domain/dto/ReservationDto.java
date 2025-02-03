package roomescape.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import roomescape.domain.entity.Reservation;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private String name;
    private String date;
    private String time;

    public static ReservationDto from(Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}