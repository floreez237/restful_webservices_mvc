package com.florian.restful_webservices_mvc.repositories;

import com.florian.restful_webservices_mvc.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);
}
