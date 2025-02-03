package roomescape.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.dto.ReservationDto;
import roomescape.domain.entity.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final List<Reservation> reservations;

    public ReservationController() {
        this.reservations = new ArrayList<>();
        reservations.add(new Reservation(1L, "브라운", "2023-01-01", "10:00"));
        reservations.add(new Reservation(2L, "브라운", "2023-01-02", "11:00"));
        reservations.add(new Reservation(3L, "브라운", "2023-01-03", "12:00"));
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "new-reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationDto> getReservations() {
        return reservations.stream()
                .map(ReservationDto::from)
                .collect(Collectors.toList());
    }
}
