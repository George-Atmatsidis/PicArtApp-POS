package com.chumbok.pos.service;

import com.chumbok.pos.entity.Category;
import com.chumbok.pos.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CategoryServiceLive implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void createNew(Category category) {
        categoryRepository.save(category);
    }
}
