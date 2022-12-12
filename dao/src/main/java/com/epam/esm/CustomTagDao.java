package com.epam.esm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomTagDao {
    Page<Tag> findTheMostPopularTagsOfUsesOrders(Pageable pageable);
}