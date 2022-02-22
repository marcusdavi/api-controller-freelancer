package com.controlefreelancer.api.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TB_SETTINGS")
public class SettingsModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    @Column(nullable = false)
    private BigDecimal maxRevenueAmount;

    @Column(nullable = false)
    private Boolean smsNotification;
    
    @Column(nullable = false)
    private Boolean emailNotification;

}
