package ba.sum.fsre.knjiznica.controller;

import ba.sum.fsre.knjiznica.model.*;
import ba.sum.fsre.knjiznica.repositories.FleetRepository;
import ba.sum.fsre.knjiznica.repositories.ReservationRepository;
import ba.sum.fsre.knjiznica.repositories.UserRepository;
import ba.sum.fsre.knjiznica.services.UserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ReservationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ReservationRepository reservationRepo;
    @Autowired
    FleetRepository fleetRepo;
    @Autowired
    UserDetailsService UserDetailsService;


    @GetMapping("/reservation")
    public String listReservation(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("activeLink", "Reservation");

        if (userDetails.getRole() == 2 || userDetails.getRole()==1) {
            List<Reservation> listReservation = reservationRepo.findAll();
            model.addAttribute("listReservation", listReservation);
            return "Reservation/index";
        } else {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            Long userId = user.getId();
            List<Reservation> listReservation = reservationRepo.findByUserId(userId);
            model.addAttribute("listReservation", listReservation);
            return "Reservation/index";
        }
    }



    @GetMapping("/reservation/add")
    public String showCreateForm(Model model) {
        List<Fleet> fleetList = fleetRepo.findAll();
        model.addAttribute("fleetList", fleetList);
        model.addAttribute("reservation", new Reservation());
        return "Reservation/create";
    }
    @GetMapping("/reservation/add/{id}")
    public String showCreate(@PathVariable("id") long id,Model model) {
        List<Fleet> fleetList = fleetRepo.findAll();
        model.addAttribute("fleetList", fleetList);
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("carid", id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("activeLink", "Reservation");
        model.addAttribute("userDetails", userDetails);
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        Long userid = user.getId();


        model.addAttribute("username", userid);
        return "Reservation/create";
    }



    @PostMapping("/reservation/add")
    public String createReservation(@Valid Reservation reservation, Model model, @RequestParam("fleet_id") Long fleetId, @RequestParam("user_id") Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("activeLink", "Reservation");
        model.addAttribute("userDetails", userDetails);
        Fleet fleet = fleetRepo.findById(fleetId).orElseThrow(() -> new IllegalArgumentException("Invalid fleet id: " + fleetId));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId));
        reservation.setFleet(fleet);
        reservation.setUser(user);

        reservationRepo.save(reservation);
        return "redirect:/reservation";
    }
    @GetMapping("/reservation/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        Reservation reservation = reservationRepo .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + id));
        reservationRepo.delete(reservation);
        return "redirect:/reservation";
    }
    @PostMapping("/reservation/potvrdi/{id}")
    public String potvrdi(@PathVariable("id") long id,Model model){
        Reservation reservation = reservationRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + id));
        reservation.setStatus(1);
        reservationRepo.save(reservation);
        return "redirect:/reservation";
    }

    @PostMapping("/reservation/odbij/{id}")
    public String odbij(@PathVariable("id") long id, Model model){
        Reservation reservation = reservationRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + id));
        reservation.setStatus(2);
        reservationRepo.save(reservation);
        return "redirect:/reservation";
    }
}
