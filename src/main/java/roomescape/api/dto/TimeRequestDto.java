package roomescape.api.dto;

import roomescape.entity.Time;

import java.time.LocalTime;

public record TimeRequestDto(
        String time
) {
    public static Time toEntity(TimeRequestDto timeRequestDto) {
        return new Time(
                LocalTime.parse(timeRequestDto.time)
        );
    }
}
