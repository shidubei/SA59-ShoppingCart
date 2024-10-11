package sg.nus.iss.shoppingCart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import sg.nus.iss.shoppingCart.model.Admin;
import sg.nus.iss.shoppingCart.repository.AdminRepository;

import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;
    @GetMapping("/admin/profile/{id}")
    public String getAdminProfile(@PathVariable("id") Integer id, Model model) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            model.addAttribute("admin", admin);
        } else {
            model.addAttribute("errorMessage", "Admin not found");
            return "error_page";
        }
        return "admin_profile";
    }
}