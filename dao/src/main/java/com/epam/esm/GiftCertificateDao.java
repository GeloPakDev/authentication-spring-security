package com.epam.esm;

import org.springframework.data.jpa.repository.JpaRepository;


public interface GiftCertificateDao extends JpaRepository<GiftCertificate, Long> , CustomCertificateDao {

}
