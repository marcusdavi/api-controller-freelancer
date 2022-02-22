package com.controlefreelancer.api.service;

import com.controlefreelancer.api.dto.response.ReportByMonthResponseDto;
import com.controlefreelancer.api.dto.response.ReportTotalResponseDto;

public interface ReportsService {

    ReportTotalResponseDto getTotal(Integer fiscalYear);

    ReportByMonthResponseDto getMonth(Integer fiscalYear);

}
