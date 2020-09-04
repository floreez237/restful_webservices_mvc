package com.florian.restful_webservices_mvc.controllers.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.florian.restful_webservices_mvc.api.v1.model.VendorDTO;
import com.florian.restful_webservices_mvc.services.interfaces.VendorService;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VendorControllerTest {

    public static final String NAME = "Lowe";
    @Mock
    VendorService vendorService;
    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityHandler())
                .build();
    }

    @Test
    void findAll() throws Exception {
        when(vendorService.findAll()).thenReturn(Arrays.asList(new VendorDTO(), new VendorDTO()));
        mockMvc.perform(get("/api/v1/vendors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    void findById() throws Exception {
        VendorDTO vendorDTO = new VendorDTO(NAME, "");
        when(vendorService.findVendorById(anyLong())).thenReturn(vendorDTO);
        mockMvc.perform(get("/api/v1/vendors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    void createVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO(NAME, "");
        VendorDTO savedDto = new VendorDTO(NAME, VendorController.BASE_URL + 1);
        when(vendorService.createVendor(any())).thenReturn(savedDto);
        mockMvc.perform(post("/api/v1/vendors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendorUrl", notNullValue()));
    }

    @Test
    void updateVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO(NAME, "");
        VendorDTO savedDto = new VendorDTO(NAME + "UPDATE", VendorController.BASE_URL + 1);
        when(vendorService.updateVendor(anyLong(), any())).thenReturn(savedDto);
        mockMvc.perform(put("/api/v1/vendors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME + "UPDATE")))
                .andExpect(jsonPath("$.vendorUrl", notNullValue()));
    }

    @Test
    void patchVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO(NAME, "");
        VendorDTO savedDto = new VendorDTO(NAME + "UPDATE", VendorController.BASE_URL + 1);
        when(vendorService.patchVendor(anyLong(), any())).thenReturn(savedDto);
        mockMvc.perform(patch("/api/v1/vendors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME + "UPDATE")))
                .andExpect(jsonPath("$.vendorUrl", notNullValue()));
    }

    @Test
    void deleteVendor() throws Exception {
        mockMvc.perform(delete("/api/v1/vendors/1"))
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