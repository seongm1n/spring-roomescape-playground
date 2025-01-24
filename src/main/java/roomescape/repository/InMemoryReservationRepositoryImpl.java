package roomescape.repository;

import roomescape.entity.Reservation;
import roomescape.exception.EntityNotFoundException;
import roomescape.service.ReservationRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryReservationRepositoryImpl implements ReservationRepository {
    private final Map<Long, Reservation> reservations;
    private final AtomicLong id = new AtomicLong(1);

    public InMemoryReservationRepositoryImpl() {
        reservations = new HashMap<>();
    }

    @Override
    public Reservation save(Reservation reservation) {
        Long newId = id.getAndIncrement();

        Reservation reservationWithId = new Reservation(
                newId,
                reservation.getPerson(),
                reservation.getDate(),
                reservation.getTime()
        );
        reservations.put(reservation.getId(), reservationWithId);

        return reservationWithId;
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.values()
                .stream()
                .sorted(Comparator.comparing(Reservation::getId).reversed())
                .toList();
    }

    @Override
    public Reservation findById(Long id) {
        return reservations.get(id);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservations.containsKey(id)) {
            throw new EntityNotFoundException("예약이 존재하지 않습니다");
        }
        reservations.remove(id);
    }
}
