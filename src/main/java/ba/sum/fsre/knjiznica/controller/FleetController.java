package ba.sum.fsre.knjiznica.controller;

import ba.sum.fsre.knjiznica.model.Book;
import ba.sum.fsre.knjiznica.model.Reservation;
import ba.sum.fsre.knjiznica.repositories.FleetRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import ba.sum.fsre.knjiznica.model.Fleet;
import ba.sum.fsre.knjiznica.model.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Controller
public class FleetController {

    @Autowired
    FleetRepository fleetRepo;

    @GetMapping("/fleet")
    public String listFleet(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        List<Fleet> listFleet = fleetRepo.findAll();
        model.addAttribute("listFleet", listFleet);
        model.addAttribute("activeLink", "Fleet");
        return "Fleet/index";
    }

    @GetMapping("/fleet/add")
    public String showAddFleetForm (Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("activeLink", "Fleet");
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("fleet", new Fleet());
        return "Fleet/create";
    }

    @PostMapping("/fleet/add")
    public String addBook (@Valid Fleet fleet, Model model) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("activeLink", "Fleet");
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("fleet", fleet);
            fleetRepo.save(fleet);
             return "redirect:/fleet";
    }
    @GetMapping("/fleet/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        Fleet fleet = fleetRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + id));
        fleetRepo.delete(fleet);
        return "redirect:/fleet";
    }

}
