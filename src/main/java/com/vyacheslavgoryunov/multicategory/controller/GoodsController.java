package com.vyacheslavgoryunov.multicategory.controller;

import com.vyacheslavgoryunov.multicategory.common.Auth;
import com.vyacheslavgoryunov.multicategory.model.Goods;
import com.vyacheslavgoryunov.multicategory.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GoodsController {
    private final GoodsRepository goodsRepository;

    public GoodsController(@Qualifier("goodsRepository") GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @GetMapping("/goods/{id}")
    public String show(@PathVariable long id, Model model) {
        Goods goods = goodsRepository.getOne(id);

        if (!Auth.hasRole("user") && goods.getCategory() != null && !goods.getCategory().getIsPublic()) {
            return "redirect:/403";
        }

        model.addAttribute("current", goods);

        return "goods/view";
    }
}
