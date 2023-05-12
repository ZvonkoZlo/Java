package ba.sum.fsre.knjiznica.controller;

import ba.sum.fsre.knjiznica.model.User;
import ba.sum.fsre.knjiznica.model.UserDetails;
import ba.sum.fsre.knjiznica.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/home")
    public String showUploadForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("activeLink", "Korisnici");
        return "/home";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("activeLink", "Korisnici");
        return "User/index";
    }
    @PostMapping("/delete")
    public String deleteOsoba(@RequestParam("id") Long id){
        userRepo.deleteById(id);
        return "redirect:/users";

    }

    @GetMapping("/edit/{id}")
    public String editOsoba(@PathVariable Long id, Model model){
        User osoba = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid osoba ID:" + id));
        model.addAttribute("osoba",osoba);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("activeLink", "User");
        return "User/edit";
    }
    @PostMapping("/update")
    public String updateOsoba(@ModelAttribute("osoba") User osoba, BindingResult result, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("activeLink", "User");
        if(result.hasErrors()){

            return "User/edit";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(osoba.getPassword());
        osoba.setPasswordRepeat(encodedPassword);
        osoba.setPassword(encodedPassword);
        userRepo.save(osoba);
        return "redirect:/users";
    }
}

