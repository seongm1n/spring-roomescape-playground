package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import roomescape.Reservation;
import roomescape.repository.QueryingDAO;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    /*
    private final QueryingDAO queryingDAO;

    public ReservationController(QueryingDAO queryingDAO){
        this.queryingDAO=queryingDAO;
    }
     */

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String Reservation(Model model) {
        model.addAttribute("reservations", reservationService.findAll());
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation request) {
        if(request.getName() == null || request.getName().isBlank() ||
                request.getDate() == null || request.getTimeId() == null){
            throw new IllegalArgumentException("데이터가 비었습니다.");
        }

        final Reservation newReservation = new Reservation(
                null,
                request.getName(),
                request.getDate(),
                request.getTimeId(),
                null // time is not required for creation
        );

        final Reservation savedReservation = reservationService.create(request);
        return ResponseEntity.created(URI.create("/reservations/"+savedReservation.getId())).body(savedReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

}