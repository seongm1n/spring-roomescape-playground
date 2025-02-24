package roomescape.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.domain.dto.ReservationRequest;
import roomescape.domain.dto.ReservationResponse;
import roomescape.domain.entity.Time;
import roomescape.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    @DisplayName("예약 목록을 조회하면 200 OK와 함께 리스트를 반환한다.")
    void findAllReservations() throws Exception {
        // given
        List<ReservationResponse> responses = List.of(
                new ReservationResponse(1L, "브라운", LocalDate.of(2023, 8, 5), new Time(null, LocalTime.of(10, 0)))
        );
        given(reservationService.findAll()).willReturn(responses);

        // when & then
        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("브라운")))
                .andExpect(jsonPath("$[0].date", is("2023-08-05")))
                .andExpect(jsonPath("$[0].time.time", is("10:00:00")));
    }

    @Test
    @DisplayName("예약을 생성하면 201 Created와 생성된 정보를 반환한다.")
    void createReservation() throws Exception {
        // given
        ReservationRequest request = new ReservationRequest(LocalDate.of(2023, 8, 5), "브라운", LocalTime.of(10, 0));
        ReservationResponse response = new ReservationResponse(1L, "브라운", LocalDate.of(2023, 8, 5), new Time(null, LocalTime.of(10, 0)));

        given(reservationService.save(Mockito.any(ReservationRequest.class))).willReturn(response);

        // when & then
        mockMvc.perform(post("/reservations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\": \"브라운\", \"date\": \"2023-08-05\", \"time\": \"10:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/reservations/1"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("브라운")))
                .andExpect(jsonPath("$.date", is("2023-08-05")))
                .andExpect(jsonPath("$.time.time", is("10:00:00")));
    }

    @Test
    @DisplayName("예약을 삭제하면 204 No Content를 반환한다.")
    void deleteReservation() throws Exception {
        // given
        doNothing().when(reservationService).deleteById(1L);

        // when & then
        mockMvc.perform(delete("/reservations/1"))
                .andExpect(status().isNoContent());
    }
}
