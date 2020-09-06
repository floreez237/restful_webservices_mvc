package com.florian.restful_webservices_mvc.api.v1.mapper;

import com.florian.restful_webservices_mvc.api.v1.model.CustomerDTO;
import com.florian.restful_webservices_mvc.controllers.v1.CustomerController;
import com.florian.restful_webservices_mvc.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "customerUrl",ignore = true)
    CustomerDTO basicCustomerToCustomerDTO(Customer customer);

    default CustomerDTO customerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = basicCustomerToCustomerDTO(customer);
        customerDTO.setCustomerUrl(CustomerController.BASE_URL + customer.getId());
        return customerDTO;
    }



    Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
