package roomescape.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.api.dto.ReservationRequestDto;
import roomescape.api.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(
            @Autowired ReservationService reservationService
    ) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> readReservations() {
        return ResponseEntity
                .ok()
                .body(
                        reservationService.readReservations().stream().
                                map(ReservationResponseDto::fromEntity)
                                .toList()
                );
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponseDto> readReservations(@PathVariable Long id) {
        Reservation reservation = reservationService.readReservation(id);

        return ResponseEntity
                .ok()
                .body(ReservationResponseDto.fromEntity(reservation));
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> createReservation(
            @RequestBody @Valid ReservationRequestDto reservationDto
    ) {
        ReservationResponseDto response =
                ReservationResponseDto.fromEntity(reservationService.createReservation(reservationDto.toEntity()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/reservations/"+ response.id())
                .body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
