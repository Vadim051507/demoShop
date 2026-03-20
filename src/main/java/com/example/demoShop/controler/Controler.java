package com.example.demoShop.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demoShop.dto.ContactRequest;
import com.example.demoShop.service.ContactService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Controler {

    private final ContactService contactService;

    public Controler(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping({"/", "/index"})
    public String home(Model model) {
        model.addAttribute("activePage", "home");
        return "index";
    }

    @GetMapping("/contacts")
    public String contacts(Model model) {
        model.addAttribute("activePage", "contacts");
        return "contacts";
    }

    @GetMapping("/catalog")
    public String catalog(Model model) {
        model.addAttribute("activePage", "catalog");
        return "catalog";
    }

    @GetMapping("/delivery")
    public String delivery(Model model) {
        model.addAttribute("activePage", "delivery");
        return "delivery";
    }

    @PostMapping("/contacts")
    public String handleContactForm(ContactRequest request, Model model) {
        try {
            contactService.sendContactEmail((request));
            model.addAttribute("successMessage", "Message sent!");
        }  catch (Exception e) {
            model.addAttribute("errorMessage", "Error. Try again.");
        }

        model.addAttribute("activePage", "contacts");
        return "contacts";
    }
}
