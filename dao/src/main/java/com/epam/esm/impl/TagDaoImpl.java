package com.epam.esm.impl;

import com.epam.esm.CustomTagDao;
import com.epam.esm.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TagDaoImpl implements CustomTagDao {

    @PersistenceContext
    protected EntityManager entityManager;

    public static final String POPULAR =
            "SELECT t " +
                    "FROM Order o " +
                    "INNER JOIN o.gifts g " +
                    "INNER JOIN g.tags t " +
                    "WHERE o.userId IN (SELECT o.userId FROM Order o GROUP BY o.userId ORDER BY SUM(o.price))" +
                    "GROUP BY t.id " +
                    "ORDER BY COUNT(t.id)";
    public static final String POPULAR_COUNT =
            "SELECT COUNT(t) " +
                    "FROM Order o " +
                    "INNER JOIN o.gifts g " +
                    "INNER JOIN g.tags t " +
                    "WHERE o.userId IN (SELECT o.userId FROM Order o GROUP BY o.userId ORDER BY SUM(o.price))" +
                    "GROUP BY t.id " +
                    "ORDER BY COUNT(t.id)";



    @Override
    public Page<Tag> findTheMostPopularTagsOfUsesOrders(Pageable pageable) {
        List<Tag> list = entityManager.createQuery(POPULAR, Tag.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(1)
                .getResultList();

        Long count = (Long) entityManager.createQuery(POPULAR_COUNT)
                .setMaxResults(1)
                .getSingleResult();

        return new PageImpl<>(list, pageable, count);
    }
}
