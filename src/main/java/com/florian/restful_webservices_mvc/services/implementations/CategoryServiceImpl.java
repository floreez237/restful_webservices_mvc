package com.florian.restful_webservices_mvc.services.implementations;

import com.florian.restful_webservices_mvc.api.v1.mapper.CategoryMapper;
import com.florian.restful_webservices_mvc.api.v1.model.CategoryDTO;
import com.florian.restful_webservices_mvc.exceptions.ResourceNotFoundException;
import com.florian.restful_webservices_mvc.repositories.CategoryRepository;
import com.florian.restful_webservices_mvc.services.interfaces.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryMapper::categoryToCategoryDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Category not Found"));
    }
}
