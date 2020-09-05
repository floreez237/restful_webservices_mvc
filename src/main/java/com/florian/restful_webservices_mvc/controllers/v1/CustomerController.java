package com.florian.restful_webservices_mvc.controllers.v1;

import com.florian.restful_webservices_mvc.api.v1.model.CustomerDTO;
import com.florian.restful_webservices_mvc.api.v1.model.CustomerListDTO;
import com.florian.restful_webservices_mvc.services.interfaces.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(value = "Basic Customer Operations")
@RequestMapping({CustomerController.BASE_URL,"/api/v1/customers",})
public class CustomerController {
    private final CustomerService customerService;
    public static final String BASE_URL = "/api/v1/customers/";


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "This will output a list of all the customers", notes = "Some Notes on the operation")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getAllCustomers() {
        return new CustomerListDTO(customerService.getAllCustomers());
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerById(@PathVariable String id) {
        Long customerID = Long.parseLong(id);
        return customerService.getCustomerDTOById(customerID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.saveCustomer(customerDTO);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(customerDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomerById(Long.parseLong(id));
    }

}
