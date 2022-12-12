package com.epam.esm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagDao extends JpaRepository<Tag, Long> , CustomTagDao {
    Optional<Tag> findByName(String name);
}