package com.florian.restful_webservices_mvc.repositories;

import com.florian.restful_webservices_mvc.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor,Long> {

}
