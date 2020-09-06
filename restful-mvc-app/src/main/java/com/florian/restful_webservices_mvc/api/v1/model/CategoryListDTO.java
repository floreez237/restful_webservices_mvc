package com.florian.restful_webservices_mvc.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryListDTO {
    private List<CategoryDTO> categories;
}
