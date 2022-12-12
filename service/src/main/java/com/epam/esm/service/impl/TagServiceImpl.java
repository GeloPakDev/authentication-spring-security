package com.epam.esm.service.impl;

import com.epam.esm.Tag;
import com.epam.esm.TagDao;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;

    @Override
    public Optional<Tag> findById(Long id) {
        return tagDao.findById(id);
    }

    @Override
    public Page<Tag> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return tagDao.findAll(pageable);
    }

    @Override
    public Tag create(Tag entity) {
        return tagDao.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Tag> tag = tagDao.findById(id);
        if (tag.isEmpty()) {
            throw new NoSuchElementException();
        }
        tagDao.deleteById(id);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagDao.findByName(name);
    }

    @Override
    public Page<Tag> findTheMostPopularTagsOfUsesOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return tagDao.findTheMostPopularTagsOfUsesOrders(pageable);
    }
}
