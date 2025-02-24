package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.dto.TimeRequest;
import roomescape.domain.dto.TimeResponse;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<TimeResponse> create(@RequestBody TimeRequest request) {
        TimeResponse response = timeService.save(request);
        return ResponseEntity.created(URI.create("/times/" + response.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> findAll() {
        return ResponseEntity.ok(timeService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
