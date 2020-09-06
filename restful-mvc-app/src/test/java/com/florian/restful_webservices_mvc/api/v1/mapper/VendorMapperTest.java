package com.florian.restful_webservices_mvc.api.v1.mapper;

import com.florian.restful_webservices_mvc.api.v1.model.VendorDTO;
import com.florian.restful_webservices_mvc.controllers.v1.VendorController;
import com.florian.restful_webservices_mvc.domain.Vendor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VendorMapperTest {

    public static final String NAME = "Lowe";
    public static final long ID = 1L;
    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    void vendorToVendorDTO() {
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        assertEquals(NAME,vendorDTO.getName());
        assertEquals(VendorController.BASE_URL+ID,vendorDTO.getVendorUrl());
    }

    @Test
    void vendorDTOToVendor() {
        VendorDTO vendorDTO = new VendorDTO(NAME, "");
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        assertEquals(NAME,vendor.getName());
    }
}