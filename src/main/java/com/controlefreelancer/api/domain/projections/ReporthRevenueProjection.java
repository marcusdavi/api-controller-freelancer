package com.controlefreelancer.api.domain.projections;

import java.math.BigDecimal;

public interface ReporthRevenueProjection {

    public Integer getMonth();

    public BigDecimal getTotal();

}
