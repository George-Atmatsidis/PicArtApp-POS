package com.chumbok.pos.service;

import com.chumbok.pos.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    void createNew(Category category);
}
