package com.florian.restful_webservices_mvc.repositories;

import com.florian.restful_webservices_mvc.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByFirstNameAndLastName(String firstName, String lastName);
}
