package com.epam.esm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

public interface CustomCertificateDao {
    Page<GiftCertificate> findWithFilters(MultiValueMap<String, String> fields, Pageable pageable);
}