package com.controlefreelancer.api.domain.model;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Accrual implements Serializable, Comparable<Accrual> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Digits(integer = 2, fraction = 0)
    private int month;

    @NotNull
    @Digits(integer = 4, fraction = 0)
    private int year;

    public Accrual() {
    }

    public Accrual(int accrualMonth, int accrualYear) {
	this.month = max(1, min(accrualMonth, 13));
	this.year = accrualYear;
    }

    @Override
    public String toString() {
	return String.format("%02d/%04d", month, year);
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(year).append(month).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}

	if (obj == null || getClass() != obj.getClass()) {
	    return false;
	}

	Accrual o = (Accrual) obj;

	return new EqualsBuilder().append(year, o.year).append(month, o.month).isEquals();
    }

    @Override
    public int compareTo(Accrual o) {
	return new CompareToBuilder().append(year, o.year).append(month, o.month).toComparison();
    }

    public Integer toInteger6() {
	return this.getYear() * 100 + this.getMonth();
    }

    public static Accrual fromInteger6(Integer yearhMonth) {
	return yearhMonth == null ? new Accrual(0, 0) : new Accrual(yearhMonth % 100, yearhMonth / 100);
    }

}
