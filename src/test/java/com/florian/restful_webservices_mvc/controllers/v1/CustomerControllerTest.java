package com.florian.restful_webservices_mvc.controllers.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.florian.restful_webservices_mvc.api.v1.model.CustomerDTO;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        mockMvc.perform(get("/api/v1/customers/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", IsCollectionWithSize.hasSize(2)));
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstName(FIRST_NAME);

        when(customerService.getCustomerDTOById(ID)).thenReturn(customerDTO);

        mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.equalTo(FIRST_NAME)));
    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Florian");
        customerDTO.setLastName("Lowe");

        CustomerDTO savedDto = new CustomerDTO();
        savedDto.setFirstName("Florian");
        savedDto.setLastName("Lowe");
        savedDto.setId(ID);
        savedDto.setCustomerUrl("/api/v1/customers/" + ID);

        when(customerService.saveCustomer(customerDTO)).thenReturn(savedDto);
        mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", CoreMatchers.equalTo("Florian")))
                .andExpect(jsonPath("$.lastName", CoreMatchers.equalTo("Lowe")))
                .andExpect(jsonPath("$.customerUrl", CoreMatchers.equalTo("/api/v1/customers/" + ID)))
                .andExpect(jsonPath("$.id", CoreMatchers.equalTo(1)));
    }

    private String asJsonString(Object o) {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}