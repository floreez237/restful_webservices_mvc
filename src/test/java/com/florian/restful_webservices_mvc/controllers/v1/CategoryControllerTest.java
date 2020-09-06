package com.florian.restful_webservices_mvc.controllers.v1;

import com.florian.restful_webservices_mvc.api.v1.mapper.CategoryMapper;
import com.florian.restful_webservices_mvc.api.v1.model.CategoryDTO;
import com.florian.restful_webservices_mvc.exceptions.ResourceNotFoundException;
import com.florian.restful_webservices_mvc.services.interfaces.CategoryService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest {
    @Mock
    CategoryService categoryService;
    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new RestResponseEntityHandler())
                .build();
    }

    @Test
    void getAllCategories() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("fruits");

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setId(1L);
        categoryDTO2.setName("fruits");

        when(categoryService.findAll()).thenReturn(Arrays.asList(categoryDTO, categoryDTO2));

        mockMvc.perform(get(CategoryController.BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", IsCollectionWithSize.hasSize(2)));

    }

    @Test
    void getCategoryByName() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Jim");

        when(categoryService.findByName(anyString())).thenReturn(categoryDTO);

        mockMvc.perform(get(CategoryController.BASE_URL + "name/Jim")
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.equalTo("Jim")));
    }

    @Test
    void testGetCategoryNameNotFound() throws Exception {
        when(categoryService.findByName(anyString())).thenThrow(new ResourceNotFoundException("Not Found Exception"));

        mockMvc.perform(get(CategoryController.BASE_URL + "name/foo"))
                .andExpect(status().isNotFound());
    }
}