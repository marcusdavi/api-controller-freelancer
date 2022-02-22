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
@Table(name = "TB_EXPENSE")
public class ExpenseModel extends TransactionModel{

    @ManyToOne
    @JoinColumn(name = "id_customer", referencedColumnName="id", nullable=true)
    private CustomerModel customer;

    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName="id", nullable=false)
    private CategoryModel category;

}
