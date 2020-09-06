package com.florian.restful_webservices_mvc.services.implementations;

import com.florian.restful_webservices_mvc.api.v1.mapper.VendorMapper;
import com.florian.restful_webservices_mvc.api.v1.model.VendorDTO;
import com.florian.restful_webservices_mvc.domain.Vendor;
import com.florian.restful_webservices_mvc.repositories.VendorRepository;
import com.florian.restful_webservices_mvc.services.interfaces.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VendorServiceImplTest {

    public static final String NAME = "Florian";
    VendorService vendorService;
    @Mock
    VendorRepository vendorRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    void findVendorById() {
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName(NAME);
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        VendorDTO vendorDTO = vendorService.findVendorById(1L);
        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    void findAll() {
        when(vendorRepository.findAll()).thenReturn(Arrays.asList(new Vendor(), new Vendor()));

        List<VendorDTO> vendorDTOS = vendorService.findAll();
        assertEquals(2,vendorDTOS.size());
    }

    @Test
    void deleteVendorById() {
        vendorService.deleteVendorById(anyLong());
        verify(vendorRepository).deleteById(anyLong());
    }

    @Test
    void saveVendor() {

        when(vendorRepository.save(any())).thenReturn(new Vendor());
        VendorDTO vendorDTO = vendorService.createVendor(new VendorDTO());

        verify(vendorRepository).save(any());
    }

    @Test
    void patchVendor() {
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName(NAME);
        VendorDTO vendorDTO = new VendorDTO(NAME,"");

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        when(vendorRepository.save(any())).thenReturn(vendor);
        VendorDTO patchedDTO = vendorService.patchVendor(1L, vendorDTO);

        assertEquals(NAME,patchedDTO.getName());
    }
}