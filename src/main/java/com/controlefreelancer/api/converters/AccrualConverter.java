package com.controlefreelancer.api.converters;

import javax.persistence.AttributeConverter;

import com.controlefreelancer.api.domain.model.Accrual;

public class AccrualConverter implements AttributeConverter<Accrual, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Accrual attribute) {
	return attribute == null ? null : attribute.toInteger6();

    }

    @Override
    public Accrual convertToEntityAttribute(Integer dbData) {
	return Accrual.fromInteger6(dbData);
    }
}