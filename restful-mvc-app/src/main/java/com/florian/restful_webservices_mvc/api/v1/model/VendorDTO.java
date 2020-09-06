package com.florian.restful_webservices_mvc.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO {
    @ApiModelProperty(name = "testName",required = true)
    private String name;
    private String vendorUrl;
}
