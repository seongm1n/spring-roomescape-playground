package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.Reservation;
import roomescape.repository.QueryingDAO;

import java.util.List;

@Service
public class ReservationService {

    private final QueryingDAO queryingDAO;

    public ReservationService(QueryingDAO queryingDAO) {
        this.queryingDAO = queryingDAO;
    }

    public List<Reservation> findAll() {
        return queryingDAO.findAll();
    }

    public Reservation create(Reservation reservation) {
        if (reservation.getName() == null || reservation.getName().isBlank() ||
                reservation.getDate() == null || reservation.getTimeId() == null) {
            throw new IllegalArgumentException("데이터가 비었습니다.");
        }
        return queryingDAO.insert(reservation);
    }

    public void delete(Long id) {
        if (!queryingDAO.delete(id)) {
            throw new IllegalArgumentException("예약이 없습니다.");
        }
    }
}
