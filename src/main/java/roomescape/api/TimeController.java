package roomescape.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.api.dto.TimeRequestDto;
import roomescape.api.dto.TimeResponseDto;
import roomescape.entity.Time;
import roomescape.service.TimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(
            @Autowired TimeService timeService
    ) {
        this.timeService = timeService;
    }
    @GetMapping
    public ResponseEntity<List<TimeResponseDto>> readTimes() {
        List<Time> times = timeService.readReservationTimes();
        List<TimeResponseDto> timeResponseDtos = times.stream()
                .map(TimeResponseDto::fromEntity)
                .toList();

        return ResponseEntity
                .ok()
                .body(timeResponseDtos);
    }

    @PostMapping
    public ResponseEntity<TimeResponseDto> createTime(@RequestBody TimeRequestDto timeRequestDto) {
        Time time = timeService.createReservationTime(TimeRequestDto.toEntity(timeRequestDto));
        TimeResponseDto timeResponseDto = TimeResponseDto.fromEntity(time);

        String headerName = "Location";
        String headerValue = "/times/" + timeResponseDto.id();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(headerName, headerValue)
                .body(timeResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteReservationTime(id);

        return ResponseEntity.noContent().build();
    }
}
