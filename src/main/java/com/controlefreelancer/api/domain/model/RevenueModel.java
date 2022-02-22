package com.controlefreelancer.api.domain.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_REVENUE")
public class RevenueModel extends TransactionModel {

    @ManyToOne
    @JoinColumn(name = "id_customer", referencedColumnName = "id", nullable = false)
    private CustomerModel customer;

}
