package ba.sum.fsre.knjiznica.controller;

import ba.sum.fsre.knjiznica.model.Fleet;
import ba.sum.fsre.knjiznica.model.Reservation;
import ba.sum.fsre.knjiznica.model.UserDetails;
import ba.sum.fsre.knjiznica.repositories.FleetRepository;
import ba.sum.fsre.knjiznica.repositories.ReservationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ReservationController {
    @Autowired
    ReservationRepository reservationRepo;
    @Autowired
    FleetRepository fleetRepo;

    @GetMapping("/reservation")
    public String listReservation(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        List<Reservation> listReservation = reservationRepo.findAll();
        model.addAttribute("listReservation", listReservation);
        model.addAttribute("activeLink", "Reservation");
        return "Reservation/index";
    }

    @GetMapping("/reservation/add")
    public String showCreateForm(Model model) {
        List<Fleet> fleetList = fleetRepo.findAll();
        model.addAttribute("fleetList", fleetList);
        model.addAttribute("reservation", new Reservation());
        return "Reservation/create";
    }


    @PostMapping("/reservation/add")
    public String createReservation(@Valid Reservation reservation,Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("activeLink", "Reservation");
        model.addAttribute("userDetails", userDetails);
        reservationRepo.save(reservation);
        return "redirect:/reservation";
    }
}
