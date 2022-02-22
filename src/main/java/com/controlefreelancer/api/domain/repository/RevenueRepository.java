package com.controlefreelancer.api.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.controlefreelancer.api.domain.model.RevenueModel;
import com.controlefreelancer.api.domain.projections.ReporthRevenueProjection;

public interface RevenueRepository extends JpaRepository<RevenueModel, Integer> {

    List<RevenueModel> findAllByTransactionDateBetween(LocalDate starts, LocalDate ends);

    @Query(value = "SELECT SUM(AMOUNT) as total, ACCRUAL_DATE as month "
	    + " FROM TB_REVENUE"
	    + " WHERE TRANSACTION_DATE between :starts and :ends"
	    + " GROUP BY ACCRUAL_DATE"
	    + " ORDER BY ACCRUAL_DATE", nativeQuery = true)
    List<ReporthRevenueProjection> findAllByTransactionDateBetweenByMonth(@Param(value = "starts") LocalDate starts,
	    @Param(value = "ends") LocalDate ends);

}
