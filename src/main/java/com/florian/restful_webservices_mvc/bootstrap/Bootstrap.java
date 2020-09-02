package com.florian.restful_webservices_mvc.bootstrap;

import com.florian.restful_webservices_mvc.domain.Category;
import com.florian.restful_webservices_mvc.domain.Customer;
import com.florian.restful_webservices_mvc.repositories.CategoryRepository;
import com.florian.restful_webservices_mvc.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeCategories();
        initializeCustomers();

    }

    private void initializeCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");


        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);


        log.debug("Category Data Loaded = " + categoryRepository.count());
    }

    private void initializeCustomers() {
        Customer customer1 = new Customer();
        customer1.setFirstName("Florian");
        customer1.setLastName("Lowe");

        Customer customer2 = new Customer();
        customer2.setFirstName("Yasmine");
        customer2.setLastName("Wiwa");

        Customer customer3 = new Customer();
        customer3.setFirstName("Nathan");
        customer3.setLastName("Lowe");

        customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));

        log.debug("Customer Data Loaded = " + customerRepository.count());

    }
}
