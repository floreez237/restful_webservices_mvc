package com.florian.restful_webservices_mvc.api.v1.mapper;

import com.florian.restful_webservices_mvc.api.v1.model.VendorDTO;
import com.florian.restful_webservices_mvc.controllers.v1.VendorController;
import com.florian.restful_webservices_mvc.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    @Mapping(target = "vendorUrl", ignore = true)
    VendorDTO basicVendorToVendorDTO(Vendor vendor);

    default VendorDTO vendorToVendorDTO(Vendor vendor) {
        VendorDTO vendorDTO = basicVendorToVendorDTO(vendor);
        vendorDTO.setVendorUrl(VendorController.BASE_URL + vendor.getId());
        return vendorDTO;
    }

    @Mapping(target = "id",ignore = true)
    Vendor vendorDTOToVendor(VendorDTO vendorDTO);



}
