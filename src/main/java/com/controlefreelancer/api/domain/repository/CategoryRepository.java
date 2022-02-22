package com.controlefreelancer.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.controlefreelancer.api.domain.model.CategoryModel;

public interface CategoryRepository extends JpaRepository<CategoryModel, Integer> {

    Page<CategoryModel> findByNameContainsIgnoreCase(String name, Pageable pageable);

}
