package ba.sum.fsre.knjiznica.repositories;

import ba.sum.fsre.knjiznica.model.Fleet;
import ba.sum.fsre.knjiznica.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
