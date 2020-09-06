package com.florian.restful_webservices_mvc.services.interfaces;

import com.florian.restful_webservices_mvc.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {

    VendorDTO findVendorById(Long vendorID);

    List<VendorDTO> findAll();

    void deleteVendorById(Long vendorId);

    VendorDTO createVendor(VendorDTO vendorDTO);

    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

    VendorDTO updateVendor(Long id, VendorDTO vendorDTO);

}
