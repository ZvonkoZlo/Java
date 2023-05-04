package ba.sum.fsre.knjiznica.repositories;

import ba.sum.fsre.knjiznica.model.Fleet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FleetRepository extends JpaRepository<Fleet, Long> {
}
