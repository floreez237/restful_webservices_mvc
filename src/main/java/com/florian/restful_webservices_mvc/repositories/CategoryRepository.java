package com.florian.restful_webservices_mvc.repositories;

import com.florian.restful_webservices_mvc.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String name);
}
