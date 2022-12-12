package com.epam.esm.service.impl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.GiftCertificateDao;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.NoResultByFiltersException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class GiftCertificateImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;


    //GET operations
    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return giftCertificateDao.findById(id);
    }

    @Override
    public Page<GiftCertificate> findAll(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return giftCertificateDao.findAll(pageRequest);
    }

    @Override
    public Page<GiftCertificate> doFilter(MultiValueMap<String, String> requestParams, int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<GiftCertificate> list = giftCertificateDao.findWithFilters(requestParams, pageRequest);
        if (list.getTotalElements() == 0) {
            throw new NoResultByFiltersException();
        }
        return list;
    }

    //CREATE operations
    @Override
    @Transactional
    public GiftCertificate create(GiftCertificate giftCertificate) {
        ExceptionResult exceptionResult = new ExceptionResult();
        //Validate GiftCertificate
        GiftValidator.validate(giftCertificate, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());

        return giftCertificateDao.save(giftCertificate);
    }

    //DELETE operations
    @Override
    @Transactional
    public void delete(Long id) {
        Optional<GiftCertificate> gift = giftCertificateDao.findById(id);
        if (gift.isEmpty()) {
            throw new NoSuchElementException();
        }
        giftCertificateDao.deleteById(id);
    }
    //UPDATE operations

    @Override
    public GiftCertificate update(Long id, GiftCertificate giftCertificate) {
        ExceptionResult exceptionResult = new ExceptionResult();
        //Get gift to update by ID
        Optional<GiftCertificate> certificate = giftCertificateDao.findById(id);
        //Extract Gift from Wrapper
        GiftCertificate gift;
        if (certificate.isPresent()) {
            gift = certificate.get();
        } else {
            throw new NoSuchElementException();
        }

        //Validate gift for update
        GiftValidator.validateForUpdate(giftCertificate, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        //Get Updated giftCertificate
        GiftCertificate updatedGift = updateGiftCertificate(giftCertificate, gift);
        //Update GiftCertificate
        return giftCertificateDao.save(updatedGift);
    }


    private GiftCertificate updateGiftCertificate(GiftCertificate giftForUpdate, GiftCertificate initialGift) {
        String name = giftForUpdate.getName();
        if (!Objects.isNull(name)) {
            initialGift.setName(name);
        }

        String description = giftForUpdate.getDescription();
        if (!Objects.isNull(description)) {
            initialGift.setDescription(description);
        }

        Double price = giftForUpdate.getPrice();
        if (!Objects.isNull(price)) {
            initialGift.setPrice(price);
        }

        int duration = giftForUpdate.getDuration();
        if (duration != 0) {
            initialGift.setDuration(duration);
        }

        initialGift.setLastUpdateDate(LocalDateTime.now());
        return initialGift;
    }
}