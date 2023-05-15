package ba.sum.fsre.knjiznica.controller;

import ba.sum.fsre.knjiznica.model.*;
import ba.sum.fsre.knjiznica.repositories.FleetRepository;
import ba.sum.fsre.knjiznica.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Controller
public class FleetController {

    @Autowired
    FleetRepository fleetRepo;
    @Autowired
    UserRepository userRepo;
   /*
    @GetMapping("/upload")
    public String showUploadForm() {
        return "Slike/create";
    }

    @PostMapping("/uploadfile")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Spremanje datoteke u "static" direktorij projekta
        Path staticDir = Paths.get("src/main/resources/static");
        Path filePath = staticDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // preusmjeravanje korisnika na stranicu za prikaz uspješnosti učitavanja
        return "redirect:/fleet";
    }*/


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
    @GetMapping("/fleet/edit/{id}")
    public String prikaziFormuEdit(@PathVariable("id") long id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("activeLink", "Fleet");

        String email = authentication.getName();
        User user = userRepo.findByEmail(email);
        if(user.getRole()== 2 || user.getRole()==1) {
            Fleet fleet = fleetRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid osoba ID:" + id));
            model.addAttribute("fleet", fleet);
            return "Fleet/edit";
        }
        else return "403";
    }
    @PostMapping("fleet/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Fleet fleet,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            fleet.setId(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("activeLink", "Fleet");
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("fleet", fleet);
            return "Fleet/edit";
        }

        fleetRepo.save(fleet);
        return "redirect:/fleet";
    }

    @GetMapping("/fleet/add")
    public String showAddFleetForm (Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("activeLink", "Fleet");
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("fleet", new Fleet());
        String email = authentication.getName();
        User user = userRepo.findByEmail(email);
        if(user.getRole()== 2 || user.getRole()==1) {
            return "Fleet/create";
        }
        else return "403";
    }

    @PostMapping("/fleet/add")
    public String addBook (@Valid Fleet fleet, Model model,@RequestParam("file") MultipartFile file) throws IOException {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("activeLink", "Fleet");
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("fleet", fleet);
       /* // Generiranje slučajnog ID-a za naziv datoteke
        String fileId = UUID.randomUUID().toString();

        // Dobivanje originalnog naziva datoteke
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Dobivanje ekstenzije datoteke
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // Novi naziv datoteke s verzijom
        String newFileName = fileId + fileExtension;*/
        try {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // Spremanje datoteke u "static" direktorij projekta
        Path staticDir = Paths.get("src/main/resources/static");
        Path filePath = staticDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        fleet.setImage(fileName);
        // preusmjeravanje korisnika na stranicu za prikaz uspješnosti učitavanja
            fleetRepo.save(fleet);
             return "redirect:/fleet";
    } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("error", "Error while adding fleet");
        return "eror";
    }
    }
    @GetMapping("/fleet/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = authentication.getName();
        User user = userRepo.findByEmail(email);
        if (user.getRole() == 2 || user.getRole() == 1) {
            Fleet fleet = fleetRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + id));
            fleetRepo.delete(fleet);
            return "redirect:/fleet";
        } else return "403";
    }
}
