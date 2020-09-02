package com.florian.restful_webservices_mvc.services.interfaces;

import com.florian.restful_webservices_mvc.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerDTOById(Long customerId);
    
}
