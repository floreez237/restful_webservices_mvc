package com.florian.restful_webservices_mvc.services.implementations;

import com.florian.restful_webservices_mvc.api.v1.mapper.CustomerMapper;
import com.florian.restful_webservices_mvc.api.v1.model.CustomerDTO;
import com.florian.restful_webservices_mvc.bootstrap.Bootstrap;
import com.florian.restful_webservices_mvc.domain.Customer;
import com.florian.restful_webservices_mvc.repositories.CategoryRepository;
import com.florian.restful_webservices_mvc.repositories.CustomerRepository;
import com.florian.restful_webservices_mvc.services.interfaces.CustomerService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CustomerServiceImplIT {

    public static final String NEW_NAME = "LOL";
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;



    CustomerService customerService;


    @BeforeEach
    void setUp() throws Exception {
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
        System.out.println("Loading Customer data");
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();

    }

    @Test
    void patchCustomerFirstName() {
        CustomerDTO customerDTO = new CustomerDTO(getFirstCustomerId(), NEW_NAME, null, null);
        CustomerDTO patchedDto = customerService.patchCustomer(customerDTO);

        assertEquals(NEW_NAME,patchedDto.getFirstName());
        assertNotNull(patchedDto.getLastName());
    }

    @Test
    void patchCustomerLastName() {
        CustomerDTO customerDTO = new CustomerDTO(getFirstCustomerId(), null, NEW_NAME, null);
        CustomerDTO patchedDto = customerService.patchCustomer(customerDTO);

        assertEquals(NEW_NAME,patchedDto.getLastName());
        assertNotNull(patchedDto.getFirstName());
    }

    @Test
    void deleteCustomer() {
        long initialCount = customerRepository.count();
        customerService.deleteCustomerById(getFirstCustomerId());
        assertEquals(initialCount-1,customerRepository.count());
    }

    /*This is because after each test the the data present in the databse is deleted and recreated
    * This causes the ID to be incremented.*/
    private Long getFirstCustomerId() {
        return customerRepository.findAll()
                .stream()
                .findFirst()
                .map(Customer::getId)
                .orElseThrow(RuntimeException::new);
    }


}