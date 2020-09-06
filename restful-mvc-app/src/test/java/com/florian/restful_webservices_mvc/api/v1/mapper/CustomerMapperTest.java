package com.florian.restful_webservices_mvc.api.v1.mapper;

import com.florian.restful_webservices_mvc.api.v1.model.CustomerDTO;
import com.florian.restful_webservices_mvc.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {
    public static final long ID = 1L;
    public static final String FIRST_NAME = "Florian";
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    @Test
    void customerToCustomerDTO() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertEquals(ID,customerDTO.getId());
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertNull(customerDTO.getLastName());
        assertNotNull(customerDTO.getCustomerUrl());
    }

    @Test
    void customerDTOToCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstName(FIRST_NAME);

        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        assertEquals(ID,customer.getId());
        assertEquals(FIRST_NAME, customer.getFirstName());
        assertNull(customer.getLastName());
    }
}