package roomescape.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import roomescape.entity.Person;
import roomescape.entity.Reservation;
import roomescape.entity.Time;
import roomescape.util.CustomDateTimeFormat;

import java.time.LocalDate;

public record ReservationRequestDto(
        @NotBlank
        @NotNull
        String name,
        @NotBlank
        @NotNull
        String date,
        @NotNull
        Long time
) {
    public Reservation toEntity() {
        return new Reservation(
            new Person(this.name),
            LocalDate.parse(date, CustomDateTimeFormat.dateFormatter),
            new Time(time)
        );
    }
}
