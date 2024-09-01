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
import roomescape.dto.request.ReservationTimeRequestDto;
import roomescape.dto.response.ReservationTimeResponseDto;
import roomescape.service.ReservationTimeService;
import roomescape.service.dto.ReservationTimeDto;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

  private final ReservationTimeService reservationTimeService;

  public ReservationTimeController(ReservationTimeService reservationTimeService) {
    this.reservationTimeService = reservationTimeService;
  }

  @GetMapping
  public ResponseEntity<List<ReservationTimeResponseDto>> readReservationTimes() {
    List<ReservationTimeDto> reservationTimes = reservationTimeService.findAll();
    List<ReservationTimeResponseDto> response =
        reservationTimes.stream()
                        .map(ReservationTimeResponseDto::from)
                        .toList();
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<ReservationTimeResponseDto> createReservationTime(@Valid @RequestBody
  final ReservationTimeRequestDto reservationTimeRequestDto) {
    ReservationTimeDto savedReservationTime = reservationTimeService.save(reservationTimeRequestDto);
    ReservationTimeResponseDto response = ReservationTimeResponseDto.from(savedReservationTime);
    return ResponseEntity.created(URI.create("/times/" + savedReservationTime.getId()))
                         .body(response);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteReservationTime(@PathVariable(name = "id") final Long id) {
    reservationTimeService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
