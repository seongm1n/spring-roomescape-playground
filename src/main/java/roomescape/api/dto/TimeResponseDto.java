package roomescape.api.dto;

import roomescape.entity.Time;
import roomescape.util.CustomDateTimeFormat;

public record TimeResponseDto(
        Long id,
        String time
) {

    public static TimeResponseDto fromEntity(Time reservationTime) {
        return new TimeResponseDto(
                reservationTime.getId(),
                reservationTime.getTime().format(CustomDateTimeFormat.timeFormatter)
        );
    }
}
