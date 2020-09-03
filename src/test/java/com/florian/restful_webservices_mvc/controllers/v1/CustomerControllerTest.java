package com.florian.restful_webservices_mvc.controllers.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.florian.restful_webservices_mvc.api.v1.model.CustomerDTO;
import com.florian.restful_webservices_mvc.domain.Customer;
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
import sun.misc.PerformanceLogger;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
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
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityHandler())
                .build();
    }

    @Test
    void getAllCustomers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(new CustomerDTO(), new CustomerDTO()));

        mockMvc.perform(get(CustomerController.BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", IsCollectionWithSize.hasSize(2)));
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstName(FIRST_NAME);

        when(customerService.getCustomerDTOById(ID)).thenReturn(customerDTO);

        mockMvc.perform(get(CustomerController.BASE_URL + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)));
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
        savedDto.setCustomerUrl(CustomerController.BASE_URL + ID);

        when(customerService.saveCustomer(customerDTO)).thenReturn(savedDto);
        mockMvc.perform(post(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("Florian")))
                .andExpect(jsonPath("$.lastName", equalTo("Lowe")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + ID)))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    void patchCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO(1L, "Florian", null, null);
        CustomerDTO patchedDto = new CustomerDTO(1L, "Florian", "Lowe", null);

        when(customerService.patchCustomer(any(CustomerDTO.class))).thenReturn(patchedDto);

        mockMvc.perform(patch(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Florian")))
                .andExpect(jsonPath("$.lastName", equalTo("Lowe")));

    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_URL + 1))
                .andExpect(status().isOk());

    }

    private String asJsonString(Object o) {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}