package roomescape.api.dto;
import roomescape.entity.Reservation;
import roomescape.util.CustomDateTimeFormat;

public record ReservationResponseDto(
        Long id,
        String name,
        String date,
        TimeResponseDto time
) {
    public static ReservationResponseDto fromEntity(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getPerson().getName(),
                reservation.getDate().format(CustomDateTimeFormat.dateFormatter),
                TimeResponseDto.fromEntity(reservation.getTime())
        );
    }
}
