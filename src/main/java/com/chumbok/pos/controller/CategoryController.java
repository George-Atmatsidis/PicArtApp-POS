package com.chumbok.pos.controller;

import com.chumbok.pos.entity.Category;
import com.chumbok.pos.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/product/addCategory", method = RequestMethod.GET)
    public ModelAndView showAddCategory() {
        ModelAndView modelAndView = new ModelAndView("addCategory");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @RequestMapping(value = "/product/addCategory", method = RequestMethod.POST)
    public String addCategory(Category category) {
        categoryService.createNew(category);
        return "redirect:/productsByPage/page/1";
    }
}
