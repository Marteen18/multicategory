package com.marteen18.multicategory.controller.admin;

import com.marteen18.multicategory.common.CategoryContainer;
import com.marteen18.multicategory.model.Category;
import com.marteen18.multicategory.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoriesController {
    private final CategoriesRepository repository;

    public CategoriesController(@Qualifier("categoriesRepository") CategoriesRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/admin/categories")
    public String categories(Model model) {
        List<Category> all = repository.findAll();
        List<CategoryContainer> containers = CategoryContainer.create(all);
        model.addAttribute("categories", containers);

        return "admin/categories/index";
    }

    @GetMapping("/admin/categories/create")
    public String create(@RequestParam(name = "parent_id", required = false, defaultValue = "0") long parentId, Model model) {
        if (parentId != 0) {
            Category parent = repository.getOne(parentId);

            if (parent != null) {
                model.addAttribute("parent", parent);
            }
        }

        return "admin/categories/view";
    }

    @GetMapping("/admin/categories/{id}/edit")
    public String edit(@PathVariable(name = "id") long id, Model model) {
        Category category = repository.getOne(id);

        if (category.getParentId() != null) {
            Optional<Category> parent = repository.findById(category.getParentId());
            parent.ifPresent(value -> model.addAttribute("parent", value));
        }

        model.addAttribute("current", category);

        return "admin/categories/view";
    }

    @PostMapping("/admin/categories")
    public RedirectView store(@RequestParam(name = "parent_id", required = false, defaultValue = "0") long parentId,
                              String title,
                              @RequestParam(name = "is_public") boolean isPublic) {
        return update(0, parentId, title, isPublic);
    }

    @PutMapping("/admin/categories/{id}")
    public RedirectView update(@PathVariable(name = "id") long id,
                               @RequestParam(name = "parent_id", required = false, defaultValue = "0") long parentId,
                               String title,
                               @RequestParam(name = "is_public") boolean isPublic) {
        boolean isNew = id == 0;
        Category category = isNew ? new Category() : repository.getOne(id);

        //restrict changing parent on exists categories
        if (isNew && parentId != 0) {
            Category parent = repository.getOne(parentId);

            if (parent != null) {
                category.setParentId(parentId);
                category.setDepth(parent.getDepth() + 1);
            }
        }

        category.setTitle(title);
        category.setIsPublic(isPublic);

        repository.save(category);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/admin/categories/" + category.getId() + "/edit");

        return redirectView;
    }

    @GetMapping("/admin/categories/{id}/delete")
    public String delete(@PathVariable(name = "id") long id, Model model) {
        Category category = repository.getOne(id);
        model.addAttribute("current", category);


        return "admin/categories/delete";
    }

    @DeleteMapping("/admin/categories/{id}")
    public RedirectView delete(@PathVariable(name = "id") long id) {
        repository.deleteById(id);
        return new RedirectView("/admin/categories");
    }
}
