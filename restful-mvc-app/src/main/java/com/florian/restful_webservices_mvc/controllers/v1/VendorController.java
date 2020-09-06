package com.florian.restful_webservices_mvc.controllers.v1;

import com.florian.restful_webservices_mvc.api.v1.model.VendorDTO;
import com.florian.restful_webservices_mvc.api.v1.model.VendorListDTO;
import com.florian.restful_webservices_mvc.services.interfaces.VendorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({VendorController.BASE_URL,"/api/v1/vendors"})
public class VendorController {
    public static final String BASE_URL = "/api/v1/vendors/";
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Will Retrieve all the vendors")
    public VendorListDTO findAll() {
        return new VendorListDTO(vendorService.findAll());
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a vendor by ID")
    public VendorDTO findById(@PathVariable String id) {
        return vendorService.findVendorById(Long.parseLong(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Used to create a vendor", notes = "A body is required")
    public VendorDTO createVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createVendor(vendorDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "To update a vendor")
    public VendorDTO updateVendor(@RequestBody VendorDTO vendorDTO, @PathVariable String id) {
        return vendorService.updateVendor(Long.parseLong(id),vendorDTO);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@RequestBody VendorDTO vendorDTO, @PathVariable String id) {
        return vendorService.patchVendor(Long.parseLong(id),vendorDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable String id) {
        vendorService.deleteVendorById(Long.parseLong(id));
    }




    }