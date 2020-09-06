package com.florian.restful_webservices_mvc.services.interfaces;

import com.florian.restful_webservices_mvc.api.v1.model.CategoryDTO;

import java.util.List;


public interface CategoryService {
    List<CategoryDTO> findAll();

    CategoryDTO findByName(String name);
}
