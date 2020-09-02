package com.florian.restful_webservices_mvc.services.implementations;

import com.florian.restful_webservices_mvc.api.v1.mapper.CustomerMapper;
import com.florian.restful_webservices_mvc.api.v1.model.CustomerDTO;
import com.florian.restful_webservices_mvc.repositories.CustomerRepository;
import com.florian.restful_webservices_mvc.services.interfaces.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerDTOById(Long customerId) {
        return customerMapper.customerToCustomerDTO(customerRepository.getOne(customerId));

    }

}
