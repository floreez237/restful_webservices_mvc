package com.florian.restful_webservices_mvc.services.implementations;

import com.florian.restful_webservices_mvc.api.v1.mapper.CustomerMapper;
import com.florian.restful_webservices_mvc.api.v1.model.CustomerDTO;
import com.florian.restful_webservices_mvc.domain.Customer;
import com.florian.restful_webservices_mvc.repositories.CustomerRepository;
import com.florian.restful_webservices_mvc.services.interfaces.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {

    public static final long ID = 1L;
    public static final String FIRST_NAME = "Florian";
    public static final String LAST_NAME = "Lowe";
    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void getAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(new Customer(), new Customer(), new Customer()));

        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();
        assertEquals(3, customerDTOS.size());
    }

    @Test
    void getCustomerDTO() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        when(customerRepository.getOne(ID)).thenReturn(customer);

        CustomerDTO customerDTO = customerService.getCustomerDTOById(ID);
        assertEquals(FIRST_NAME,customerDTO.getFirstName());
        assertEquals(LAST_NAME,customerDTO.getLastName());
    }
}