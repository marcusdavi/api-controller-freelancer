package com.controlefreelancer.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import com.controlefreelancer.api.domain.model.SettingsModel;
import com.controlefreelancer.api.dto.SettingsDto;
import com.controlefreelancer.api.service.SettingsService;

@RunWith(MockitoJUnitRunner.class)
public class SettingsControllerTest {

    @InjectMocks
    private SettingsController controller;

    @Mock
    private SettingsService service;

    @Test
    public void testGetOk() {

	SettingsModel settings = new SettingsModel();
	settings.setEmailNotification(true);
	settings.setSmsNotification(true);
	settings.setId(1);
	settings.setMaxRevenueAmount(BigDecimal.valueOf(80000));

	when(service.getSettings()).thenReturn(settings);

	SettingsModel response = controller.settings().getBody();

	assertEquals(BigDecimal.valueOf(80000), response.getMaxRevenueAmount());
	assertTrue(response.getEmailNotification());
	assertTrue(response.getSmsNotification());

    }
    
    @Test
    public void testUpdateOk() {

	SettingsModel settings = new SettingsModel();
	settings.setEmailNotification(false);
	settings.setSmsNotification(true);
	settings.setId(1);
	settings.setMaxRevenueAmount(BigDecimal.valueOf(80000));
	
	SettingsDto settingsDto = new SettingsDto();
	BeanUtils.copyProperties(settings, settingsDto);
	settingsDto.setEmailNotification(false);

	when(service.update(settingsDto)).thenReturn(settings);

	SettingsModel response = controller.settings(settingsDto).getBody();

	assertEquals(BigDecimal.valueOf(80000), response.getMaxRevenueAmount());
	assertFalse(response.getEmailNotification());
	assertTrue(response.getSmsNotification());

    }

    @Test
    public void testUpdateOK() {

    }

}
