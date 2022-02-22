package com.controlefreelancer.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingsDto implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @NotNull
    private BigDecimal maxRevenueAmount;

    @NotNull
    private Boolean smsNotification;
    
    @NotNull
    private Boolean emailNotification;

}
