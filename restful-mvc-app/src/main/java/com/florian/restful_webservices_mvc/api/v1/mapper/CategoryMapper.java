package com.florian.restful_webservices_mvc.api.v1.mapper;

import com.florian.restful_webservices_mvc.api.v1.model.CategoryDTO;
import com.florian.restful_webservices_mvc.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper{

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);


    CategoryDTO categoryToCategoryDTO(Category category);

    Category CategoryDTOToCategory(CategoryDTO categoryDTO);
}
