package com.florian.restful_webservices_mvc.services.implementations;

import com.florian.restful_webservices_mvc.api.v1.mapper.CategoryMapper;
import com.florian.restful_webservices_mvc.api.v1.model.CategoryDTO;
import com.florian.restful_webservices_mvc.domain.Category;
import com.florian.restful_webservices_mvc.repositories.CategoryRepository;
import com.florian.restful_webservices_mvc.services.interfaces.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelExtensionsKt;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "Fruit";
    @Mock
    CategoryRepository categoryRepository;
    CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);
    }


    @Test
    void findAll() {
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));
        List<CategoryDTO> categoryDTOS = categoryService.findAll();

        assertEquals(1,categoryDTOS.size());
        assertEquals(NAME,categoryDTOS.get(0).getName());
        assertEquals(ID,categoryDTOS.get(0).getId());
    }

    @Test
    void findByName() {
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        when(categoryRepository.findByName(NAME)).thenReturn(category);

        CategoryDTO categoryDTO = categoryService.findByName(NAME);

        assertNotNull(categoryDTO);
        assertEquals(ID,categoryDTO.getId());
    }
}