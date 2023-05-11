package ba.sum.fsre.knjiznica.repositories;

import ba.sum.fsre.knjiznica.model.Fleet;
import ba.sum.fsre.knjiznica.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Pronađi sve rezervacije za korisnika s određenim id
    List<Reservation> findByUserId(Long userId);
}
