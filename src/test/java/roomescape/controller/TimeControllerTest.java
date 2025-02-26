package roomescape.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.domain.dto.TimeRequest;
import roomescape.domain.dto.TimeResponse;
import roomescape.domain.entity.Time;
import roomescape.service.TimeService;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TimeController.class)
public class TimeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimeService timeService;

    @Test
    void testFindAll() throws Exception {
        when(timeService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/times"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testCreate() throws Exception {
        TimeResponse response = new TimeResponse(new Time(1L, LocalTime.of(10, 0)));

        when(timeService.save(any(TimeRequest.class))).thenReturn(response);

        mockMvc.perform(post("/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "time": "10:00"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/times/1"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.time").value("10:00:00"));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/times/1"))
                .andExpect(status().isNoContent());
    }
}
