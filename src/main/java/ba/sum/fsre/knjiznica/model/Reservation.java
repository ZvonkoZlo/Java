package ba.sum.fsre.knjiznica.model;

import jakarta.persistence.*;
import ba.sum.fsre.knjiznica.model.Fleet;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


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

    public static long getBrojDana(LocalDateTime pocetak, LocalDateTime kraj) {
        LocalDate pocetakDate = pocetak.toLocalDate();
        LocalDate krajDate = kraj.toLocalDate();
        return ChronoUnit.DAYS.between(pocetakDate, krajDate);
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fleet_id")
    private Fleet fleet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // getters and setters
}
