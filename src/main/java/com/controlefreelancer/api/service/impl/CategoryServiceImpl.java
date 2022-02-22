package com.controlefreelancer.api.service.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.controlefreelancer.api.domain.model.CategoryModel;
import com.controlefreelancer.api.domain.repository.CategoryRepository;
import com.controlefreelancer.api.dto.CategoryDto;
import com.controlefreelancer.api.exception.ResourceNotFoundException;
import com.controlefreelancer.api.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MessageSource messageSource;

    public CategoryServiceImpl(MessageSource messageSource, CategoryRepository categoryRepository) {
	this.messageSource = messageSource;
	this.categoryRepository = categoryRepository;
    }

    public Page<CategoryModel> findByName(String name, Pageable pageable) {
	return categoryRepository.findByNameContainsIgnoreCase(name, pageable);
    }

    public List<CategoryModel> list() {
	return categoryRepository.findAll();
    }

    public Optional<CategoryModel> get(Integer id) {
	return categoryRepository.findById(id);
    }

    public CategoryModel create(CategoryDto category) {
	CategoryModel categoryModel = new CategoryModel();
	BeanUtils.copyProperties(category, categoryModel);
	categoryModel.setActive(true);
	return categoryRepository.save(categoryModel);

    }

    public void archive(Integer id) {

	CategoryModel categoryUpdate = validExistenceCategory(id);
	categoryUpdate.setActive(false);
	categoryRepository.save(categoryUpdate);

    }

    private CategoryModel validExistenceCategory(Integer id) {
	return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
		messageSource.getMessage("category-not-found", null, LocaleContextHolder.getLocale())));
    }

    public CategoryModel update(Integer id, @Valid CategoryDto category) {

	CategoryModel categoryUpdate = validExistenceCategory(id);
	BeanUtils.copyProperties(category, categoryUpdate, "id");
	return categoryRepository.save(categoryUpdate);
    }

}
