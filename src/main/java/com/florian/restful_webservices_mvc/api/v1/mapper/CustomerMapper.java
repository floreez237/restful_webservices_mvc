package com.florian.restful_webservices_mvc.api.v1.mapper;

import com.florian.restful_webservices_mvc.api.v1.model.CustomerDTO;
import com.florian.restful_webservices_mvc.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
