package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.request.ReservationRequestDto;
import roomescape.dto.response.ReservationResponseDto;
import roomescape.service.dto.ReservationDto;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

  private final ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping
  public ResponseEntity<List<ReservationResponseDto>> readReservation() {
    List<ReservationDto> reservations = reservationService.findAll();
    List<ReservationResponseDto> response =
        reservations.stream()
                    .map(ReservationResponseDto::from)
                    .toList();
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<ReservationResponseDto> createReservation(@Valid @RequestBody final ReservationRequestDto reservationRequestDto) {
    ReservationDto savedReservation = reservationService.save(reservationRequestDto);
    ReservationResponseDto response = ReservationResponseDto.from(savedReservation);
    return ResponseEntity.created(URI.create("/reservations/" + savedReservation.getId()))
                         .body(response);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteReservation(@PathVariable(name = "id") Long id) {
    reservationService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
