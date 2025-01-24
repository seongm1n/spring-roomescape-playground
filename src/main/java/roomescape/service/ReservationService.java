package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.entity.Reservation;
import roomescape.entity.Time;
import roomescape.repository.ReservationDao;
import roomescape.repository.TimeDao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {
    private static final int RESERVATION_RUNNING_TIME = 60;
    private static final int RESERVATION_CLEAN_TIME = 30;
    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(
            @Autowired ReservationDao reservationRepository,
            @Autowired TimeDao timeDao
    ) {
        this.reservationDao = reservationRepository;
        this.timeDao = timeDao;
    }

    public List<Reservation> readReservations() {
        return reservationDao.findAll();
    }

    public Reservation readReservation(Long id) {
        return reservationDao.findById(id);
    }

    public Reservation createReservation(Reservation reservationDto) {
        reservationDto.validateReservationDate();

        Time time = timeDao.findById(reservationDto.getTimeId());

        Reservation currReservation = new Reservation(
                reservationDto.getPerson(),
                reservationDto.getDate(),
                time
        );

        List<Reservation> savedReservations = reservationDao.findByDate(currReservation.getDate());

        validateReservationTime(currReservation, savedReservations);

        return reservationDao.save(currReservation, currReservation.getTimeId());
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }

    private void validateReservationTime(Reservation currReservation, List<Reservation> savedReservations) {
        LocalTime currReservationTime = currReservation.getLocalTime();

        List<LocalTime> savedTimes = savedReservations
                .stream()
                .map(Reservation::getLocalTime)
                .toList();

        long reservationLimitTime = RESERVATION_RUNNING_TIME + RESERVATION_CLEAN_TIME;

        boolean conflictExists = savedTimes.stream().
                anyMatch(s -> {
                    LocalTime upperBound = s.plusMinutes(reservationLimitTime);
                    return currReservationTime.equals(s) || (currReservationTime.isBefore(upperBound) && currReservationTime.isAfter(s));
                });

        if (conflictExists) {
           throw new IllegalArgumentException("예약 불가능: 1시간 30분 이내의 예약이 이미 존재합니다.");
        }
    }
}
