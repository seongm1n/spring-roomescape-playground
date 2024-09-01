package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.Time;
import roomescape.repository.TimeDAO;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/times")
public class TimeController {

    private final TimeDAO timeDAO;

    public TimeController(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Time>> getTimes() {
        return ResponseEntity.ok(timeDAO.findAll());
    }

    @PostMapping
    public ResponseEntity<Time> createTime(@RequestBody Time request) {
        if (request.getTime() == null) {
            throw new IllegalArgumentException("시간 데이터가 비어있습니다.");
        }

        final Time newTime = timeDAO.insert(request);
        return ResponseEntity.created(URI.create("/times/" + newTime.getId())).body(newTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        boolean flag = timeDAO.delete(id);

        if (!flag) {
            throw new IllegalArgumentException("시간이 없습니다.");
        }

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }
}
