package com.marteen18.multicategory.controller;

import com.marteen18.multicategory.common.Auth;
import com.marteen18.multicategory.common.CategoryContainer;
import com.marteen18.multicategory.model.Category;
import com.marteen18.multicategory.model.Goods;
import com.marteen18.multicategory.repository.CategoriesRepository;
import com.marteen18.multicategory.repository.GoodsRepository;
import com.marteen18.multicategory.service.CategoriesService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {
    private final CategoriesRepository categoriesRepository;
    private final GoodsRepository goodsRepository;

    private final CategoriesService categoriesService;

    public HomeController(@Qualifier("categoriesRepository") CategoriesRepository categories,
                          @Qualifier("goodsRepository") GoodsRepository goods,
                          CategoriesService categoriesService) {
        this.categoriesRepository = categories;
        this.goodsRepository = goods;
        this.categoriesService = categoriesService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        return home(0, model);
    }

    @GetMapping("/category/{categoryId}")
    public String home(@PathVariable(name = "categoryId", required = false) long categoryId, Model model) {
        boolean isGuest = !Auth.hasRole("user");

        List<Goods> goods = null;

        //it should be refactored
        //but now I don't know how to modify JpaRepository to have a better result
        if (categoryId == 0) {
            goods = isGuest ?
                    goodsRepository.findAllByCategory_IsPublicOrCategoryIsNull(true) :
                    goodsRepository.findAll();
        } else {
            List<Long> sequence = categoriesService.getChildSequence(categoryId);
            goods = isGuest ?
                    goodsRepository.findAllByCategory_IdInAndCategory_IsPublic(sequence, true) :
                    goodsRepository.findAllByCategory_IdIn(sequence);
        }

        List<Category> categories = isGuest ?
                categoriesRepository.findAllByIsPublic(true) :
                categoriesRepository.findAll();

        model.addAttribute("categories", CategoryContainer.create(categories));
        model.addAttribute("goods", goods);
        model.addAttribute("categoryId", categoryId);

        return "home/index";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "errors/403";
    }

    @GetMapping("/error")
    public String error() {
        return "errors/general";
    }
}
