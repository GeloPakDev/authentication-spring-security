package com.epam.esm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

import static com.epam.esm.ColumnName.*;

@Entity
@Table(name = GIFT_TABLE_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = GIFT_NAME)
    private String name;

    @NotNull
    @Column(name = GIFT_DESCRIPTION)
    private String description;

    @NotNull
    @Column(name = GIFT_PRICE)
    private Double price;

    @NotNull
    @Column(name = GIFT_DURATION)
    private int duration;

    @Column(name = GIFT_CREATE_DATE)
    private LocalDateTime createDate;

    @Column(name = GIFT_LAST_UPDATE_DATE)
    private LocalDateTime lastUpdateDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = GIFT_TAGS,
            joinColumns = {@JoinColumn(name = LINK_GIFT_ID)},
            inverseJoinColumns = {@JoinColumn(name = LINK_TAG_ID)})
    private Set<Tag> tags;

}