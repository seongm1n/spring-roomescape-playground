package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.entity.Reservation;
import roomescape.entity.Time;
import roomescape.exception.TimeDeleteException;
import roomescape.repository.ReservationDao;
import roomescape.repository.TimeDao;

import java.util.List;

@Service
public class TimeService {
    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public TimeService(
            @Autowired ReservationDao reservationDao,
            @Autowired TimeDao timeDao
    ) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public List<Time> readReservationTimes() {
        return timeDao.findAll();
    }

    public Time createReservationTime(roomescape.entity.Time reservationTime) {
        return timeDao.save(reservationTime);
    }

    public void deleteReservationTime(Long id) {
        List<Reservation> reservations = reservationDao.findByTimeId(id);
        if (!reservations.isEmpty()) {
            throw new TimeDeleteException("해당 시간 id의 예약이 존재합니다");
        }
        
        timeDao.delete(id);
    }
}
