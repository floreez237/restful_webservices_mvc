package com.florian.restful_webservices_mvc.services.implementations;

import com.florian.restful_webservices_mvc.api.v1.mapper.CustomerMapper;
import com.florian.restful_webservices_mvc.api.v1.model.CustomerDTO;
import com.florian.restful_webservices_mvc.domain.Customer;
import com.florian.restful_webservices_mvc.exceptions.ResourceNotFoundException;
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
        /*final Customer customer = customerRepository.getOne(customerId);
        return customerMapper.customerToCustomerDTO(customer);*/
        return customerRepository.findById(customerId)
                .map(customerMapper::customerToCustomerDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        return customerMapper.customerToCustomerDTO(customerRepository.save(customer));
    }

    @Override
    public CustomerDTO patchCustomer(CustomerDTO customerDTO) {
        return customerRepository.findById(customerDTO.getId())
                .map(customer -> {
                    if (customerDTO.getFirstName() != null) {
                        customer.setFirstName(customerDTO.getFirstName());
                    }
                    if (customerDTO.getLastName() != null) {
                        customer.setLastName(customerDTO.getLastName());
                    }

                    return customerMapper.customerToCustomerDTO(customerRepository.save(customer));
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

}
