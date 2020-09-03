package com.florian.restful_webservices_mvc.controllers.v1;

import com.florian.restful_webservices_mvc.api.v1.model.CustomerDTO;
import com.florian.restful_webservices_mvc.api.v1.model.CustomerListDTO;
import com.florian.restful_webservices_mvc.services.interfaces.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@Controller
@RequestMapping({CustomerController.BASE_URL,"/api/v1/customers",})
public class CustomerController {
    private final CustomerService customerService;
    public static final String BASE_URL = "/api/v1/customers/";


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerListDTO> getAllCustomers() {
        return new ResponseEntity<>(
                new CustomerListDTO(customerService.getAllCustomers()),
                HttpStatus.OK
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable String id) {
        Long customerID = Long.parseLong(id);
        return new ResponseEntity<>(customerService.getCustomerDTOById(customerID),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.saveCustomer(customerDTO),HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<CustomerDTO> patchCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.patchCustomer(customerDTO),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomerById(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
