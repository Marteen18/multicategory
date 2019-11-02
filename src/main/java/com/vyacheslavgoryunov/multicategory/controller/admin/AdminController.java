package com.vyacheslavgoryunov.multicategory.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AdminController {
    @GetMapping("/admin/home")
    public RedirectView index() {
        return new RedirectView("/admin/goods");
    }
}
