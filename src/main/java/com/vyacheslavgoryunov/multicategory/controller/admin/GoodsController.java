package com.vyacheslavgoryunov.multicategory.controller.admin;

import com.google.gson.Gson;
import com.vyacheslavgoryunov.multicategory.common.Auth;
import com.vyacheslavgoryunov.multicategory.common.DetailNode;
import com.vyacheslavgoryunov.multicategory.common.UserDetails;
import com.vyacheslavgoryunov.multicategory.model.Category;
import com.vyacheslavgoryunov.multicategory.model.Goods;
import com.vyacheslavgoryunov.multicategory.model.User;
import com.vyacheslavgoryunov.multicategory.repository.CategoriesRepository;
import com.vyacheslavgoryunov.multicategory.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller("adminGoodsController")
public class GoodsController {
    private final GoodsRepository goods;
    private final CategoriesRepository categories;

    public GoodsController(@Qualifier("goodsRepository") GoodsRepository repository,
                           @Qualifier("categoriesRepository") CategoriesRepository categories) {
        this.goods = repository;
        this.categories = categories;
    }

    @GetMapping("/admin/goods")
    public String index(Model model) {
        model.addAttribute("goodsList", goods.findAll());
        return "admin/goods/index";
    }

    @GetMapping("/admin/goods/create")
    public String create(Model model) {
        return edit(0, model);
    }

    @PostMapping("/admin/goods")
    public RedirectView store(Goods goods,
                              @RequestParam(name = "category_id", required = false, defaultValue = "0") long categoryId) {
        return update(goods, categoryId);
    }

    @GetMapping("/admin/goods/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        if (id != 0) {
            Goods goods = this.goods.getOne(id);
            model.addAttribute("current", goods);
        }

        model.addAttribute("categories", this.categories.findAll());

        return "admin/goods/view";
    }

    @PutMapping("/admin/goods/{id}")
    public RedirectView update(Goods goods,
                               @RequestParam(name = "category_id", required = false, defaultValue = "0") long categoryId) {
        boolean isNew = goods.getId() == 0;

        if (categoryId > 0) {
            goods.setCategory(categories.getOne(categoryId));
        }

        if (isNew) {
            UserDetails user = Auth.currentUserDetails();
            goods.setUser(new User(user.getId()));
        }

        goods.setDetailsRaw(new Gson().toJson(goods.getDetails()));

        this.goods.save(goods);

        return new RedirectView("/admin/goods/" + goods.getId() + "/edit");
    }

    @GetMapping("/admin/goods/{id}/delete")
    public String delete(@PathVariable(name = "id") long id, Model model) {
        Goods goods = this.goods.getOne(id);
        model.addAttribute("current", goods);

        return "admin/goods/delete";
    }

    @DeleteMapping("/admin/goods/{id}")
    public RedirectView delete(@PathVariable(name = "id") long id) {
        goods.deleteById(id);
        return new RedirectView("/admin/goods");
    }
}
