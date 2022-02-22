package com.controlefreelancer.api.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.controlefreelancer.api.domain.model.CategoryModel;
import com.controlefreelancer.api.dto.CategoryDto;

public interface CategoryService {

    List<CategoryModel> list();

    Optional<CategoryModel> get(Integer id);

    CategoryModel create(CategoryDto category);

    void archive(Integer id);

    CategoryModel update(Integer id, @Valid CategoryDto category);

    Page<CategoryModel> findByName(String name, Pageable pageable);
}