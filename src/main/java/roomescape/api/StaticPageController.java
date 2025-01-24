package roomescape.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticPageController {
    @GetMapping("/")
    public String mainPage() {
        return "home";
    }


    @GetMapping("/reservation")
    public String reservationPage() {
        return "new-reservation";
    }

    @GetMapping("/time")
    public String timesPage() {
        return "time";
    }
}
