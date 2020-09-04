package com.florian.restful_webservices_mvc.services.implementations;

import com.florian.restful_webservices_mvc.api.v1.mapper.VendorMapper;
import com.florian.restful_webservices_mvc.api.v1.model.VendorDTO;
import com.florian.restful_webservices_mvc.domain.Vendor;
import com.florian.restful_webservices_mvc.exceptions.ResourceNotFoundException;
import com.florian.restful_webservices_mvc.repositories.VendorRepository;
import com.florian.restful_webservices_mvc.services.interfaces.VendorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransaction;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public VendorDTO findVendorById(Long vendorID) {
        return vendorRepository.findById(vendorID)
                .map(vendorMapper::vendorToVendorDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<VendorDTO> findAll() {
        return vendorRepository.findAll()
                .stream()
                .map(vendorMapper::vendorToVendorDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteVendorById(Long vendorId) {
        vendorRepository.deleteById(vendorId);
    }

    @Override
    public VendorDTO createVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        return vendorMapper.vendorToVendorDTO(
                vendorRepository.save(vendor)
        );
    }



    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    final String vendorDTOName = vendorDTO.getName();
                    if (vendorDTOName != null) {
                        vendor.setName(vendorDTOName);
                    }
                    return vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);
        return vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
    }
}
