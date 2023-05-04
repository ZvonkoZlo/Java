package ba.sum.fsre.knjiznica.controller;

import ba.sum.fsre.knjiznica.model.User;
import ba.sum.fsre.knjiznica.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/")
    public String index (Model model) {
        return "redirect:users";
    }
    @GetMapping("/register")
    public String showRegistrationForm (Model model) {
        model.addAttribute("user", new User());
        return "register_form";
    }

    @PostMapping("/register_user")
    public String registerUser (@Valid User user, BindingResult result, Model model) {
        boolean errors = result.hasErrors();
        if (errors) {
            return "register_form";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPasswordRepeat(encodedPassword);
        user.setPassword(encodedPassword);
        userRepo.save(user);
        return "register_success";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("user", new User());
        return "login_form";
    }
}
