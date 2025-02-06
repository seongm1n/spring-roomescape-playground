package roomescape.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.entity.Reservation;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    public List<Reservation> getReservations() {
        return reservations;
    }
}
