package ba.sum.fsre.knjiznica.model;

import jakarta.persistence.*;
import ba.sum.fsre.knjiznica.model.Fleet;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPocetakRezervacije() {
        return pocetakRezervacije;
    }

    public void setPocetakRezervacije(LocalDateTime pocetakRezervacije) {
        this.pocetakRezervacije = pocetakRezervacije;
    }

    public LocalDateTime getKrajRezervacije() {
        return krajRezervacije;
    }

    public void setKrajRezervacije(LocalDateTime krajRezervacije) {
        this.krajRezervacije = krajRezervacije;
    }

    public Fleet getFleet() {
        return fleet;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime pocetakRezervacije;

    private LocalDateTime krajRezervacije;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fleet_id")
    private Fleet fleet;

    // getters and setters
}
