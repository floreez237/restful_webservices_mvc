package com.florian.restful_webservices_mvc.controllers.v1;

import com.florian.restful_webservices_mvc.api.v1.model.CustomerDTO;
import com.florian.restful_webservices_mvc.services.interfaces.CategoryService;
import com.florian.restful_webservices_mvc.services.interfaces.CustomerService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {

    public static final long ID = 1L;
    public static final String FIRST_NAME = "Florian";
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(new CustomerDTO(), new CustomerDTO()));

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", IsCollectionWithSize.hasSize(2)));
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstName(FIRST_NAME);

        when(customerService.getCustomerDTOById(ID)).thenReturn(customerDTO);

        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.equalTo(FIRST_NAME)));
    }
}