package com.controlefreelancer.api.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.controlefreelancer.api.domain.model.CategoryModel;
import com.controlefreelancer.api.dto.CategoryDto;
import com.controlefreelancer.api.service.CategoryService;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {

    private static final int ID_CATEGORY_01 = 1;
    private static final int ID_CATEGORY_02 = 2;

    private static final String DESCRIPTION_CATEGORY_01 = "category 01 - test";
    private static final String DESCRIPTION_CATEGORY_02 = "category 02 - test";

    private static final String CATEGORY_01 = "category 01";
    private static final String CATEGORY_02 = "category 02";

    @InjectMocks
    private CategoryController controller;

    @Mock
    private CategoryService service;

    @Mock
    private ApplicationEventPublisher publisher;

    @Test
    public void testListOk() {

	List<CategoryModel> categories = new ArrayList<>();
	categories.add(buildCategoryModel(ID_CATEGORY_01, CATEGORY_01, DESCRIPTION_CATEGORY_01));
	categories.add(buildCategoryModel(ID_CATEGORY_02, CATEGORY_02, DESCRIPTION_CATEGORY_02));

	when(service.list()).thenReturn(categories);

	List<CategoryModel> response = controller.list().getBody();

	assertTrue(!response.isEmpty());
	assertEquals(2, response.size());
	assertEquals(ID_CATEGORY_01, response.get(0).getId());
	assertEquals(CATEGORY_01, response.get(0).getName());
	assertEquals(ID_CATEGORY_02, response.get(1).getId());
	assertEquals(CATEGORY_02, response.get(1).getName());
	assertEquals(DESCRIPTION_CATEGORY_02, response.get(1).getDescription());

    }

    @Test
    public void testListEmptyOk() {

	when(service.list()).thenReturn(new ArrayList<>());

	List<CategoryModel> response = controller.list().getBody();

	assertTrue(response.isEmpty());

    }

    @Test
    public void testGetCategoryOk() {

	when(service.get(Mockito.anyInt()))
		.thenReturn(Optional.of(buildCategoryModel(1, CATEGORY_01, DESCRIPTION_CATEGORY_01)));

	ResponseEntity<CategoryModel> response = controller.get(1);

	assertNotNull(response.getBody());
	assertEquals(ID_CATEGORY_01, response.getBody().getId());
	assertEquals(CATEGORY_01, response.getBody().getName());
	assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testGetCategorynotFound() {

	when(service.get(Mockito.anyInt())).thenReturn(Optional.empty());

	ResponseEntity<CategoryModel> response = controller.get(1);

	assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	assertNull(response.getBody());

    }

    @Test
    public void testGetCategoryByName() {

	List<CategoryModel> categories = new ArrayList<>();
	categories.add(buildCategoryModel(1, CATEGORY_01, DESCRIPTION_CATEGORY_01));
	categories.add(buildCategoryModel(2, CATEGORY_02, DESCRIPTION_CATEGORY_02));

	PageRequest page = PageRequest.of(0, 10);

	Page<CategoryModel> categoriesPage = new PageImpl<>(categories, page, categories.size());

	when(service.findByName(Mockito.anyString(), Mockito.any())).thenReturn(categoriesPage);

	Page<CategoryModel> response = controller.findByName("IC", page).getBody();

	assertTrue(!response.isEmpty());
	assertEquals(2L, response.getTotalElements());
	assertEquals(ID_CATEGORY_01, response.get().findFirst().get().getId());
	assertEquals(CATEGORY_01, response.get().findFirst().get().getName());

    }

    @Test
    public void testCreateCategoryOk() {
	CategoryModel category = buildCategoryModel(1, CATEGORY_01, DESCRIPTION_CATEGORY_01);
	CategoryDto categoryDto = buildCategoryDto(CATEGORY_01, DESCRIPTION_CATEGORY_01);

	when(service.create(Mockito.any())).thenReturn(category);

	ResponseEntity<CategoryModel> response = controller.create(categoryDto, null);

	assertNotNull(response.getBody());
	assertEquals(ID_CATEGORY_01, response.getBody().getId());
	assertEquals(CATEGORY_01, response.getBody().getName());
	assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testDeleteOk() {

	ResponseEntity<Void> response = controller.archive(1);

	assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testUpdateOk() {

	CategoryModel category = buildCategoryModel(1, CATEGORY_01, DESCRIPTION_CATEGORY_01);
	CategoryDto categoryDto = buildCategoryDto(CATEGORY_01, DESCRIPTION_CATEGORY_01);

	when(service.update(Mockito.anyInt(), Mockito.any())).thenReturn(category);

	ResponseEntity<CategoryModel> response = controller.update(1, categoryDto);

	assertNotNull(response.getBody());
	assertEquals(ID_CATEGORY_01, response.getBody().getId());
	assertEquals(CATEGORY_01, response.getBody().getName());
	assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private CategoryModel buildCategoryModel(Integer id, String name, String description) {
	CategoryModel category = new CategoryModel();
	category.setId(id);
	category.setName(name);
	category.setDescription(description);
	return category;
    }

    private CategoryDto buildCategoryDto(String name, String description) {
	return CategoryDto.builder().name(name).description(description).build();
    }
}
