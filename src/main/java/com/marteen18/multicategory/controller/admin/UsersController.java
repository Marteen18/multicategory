package com.marteen18.multicategory.controller.admin;

import com.marteen18.multicategory.model.User;
import com.marteen18.multicategory.repository.UsersRepository;
import com.marteen18.multicategory.service.UsersService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class UsersController {
    private final UsersRepository repository;
    private final UsersService service;

    public UsersController(@Qualifier("usersRepository") UsersRepository usersRepository, UsersService service) {
        this.repository = usersRepository;
        this.service = service;
    }

    @GetMapping("/admin/users")
    public String index(Model model) {
        List<User> users = repository.findAll();
        model.addAttribute("users", users);

        return "admin/users/index";
    }

    @GetMapping("/admin/users/create")
    public String create(Model model) {
        return edit(0, model);
    }

    @GetMapping("/admin/users/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        if (id != 0) {
            User user = repository.getOne(id);
            model.addAttribute("current", user);
        }

        return "admin/users/view";
    }

    @PostMapping("/admin/users")
    public RedirectView store(String name,
                              String email,
                              String password,
                              @RequestParam("password_confirmation") String passwordConfirmation) {
        if (repository.countByEmail(email) > 0) {
            return new RedirectView("/admin/users/create?emailError=true");
        }

        return update(0, name, email, password, passwordConfirmation);
    }

    @PutMapping("/admin/users/{id}")
    public RedirectView update(@PathVariable long id,
                               String name,
                               String email,
                               String password,
                               @RequestParam("password_confirmation") String passwordConfirmation) {
        User user = id == 0 ? new User() : repository.getOne(id);
        user.setName(name);
        user.setEmail(email);

        boolean changePassword = !password.isEmpty() && password.equals(passwordConfirmation);
        if (changePassword) {
            user.setPassword(password);
        }

        service.saveUser(user, changePassword);

        return new RedirectView("/admin/users/" + user.getId() + "/edit");
    }

    @GetMapping("/admin/users/{id}/delete")
    public String delete(@PathVariable(name = "id") long id, Model model) {
        User user = repository.getOne(id);
        model.addAttribute("current", user);

        return "admin/users/delete";
    }

    @DeleteMapping("/admin/users/{id}")
    public RedirectView delete(@PathVariable(name = "id") long id) {
        repository.deleteById(id);
        return new RedirectView("/admin/users");
    }
}
