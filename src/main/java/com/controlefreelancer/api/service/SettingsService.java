package com.controlefreelancer.api.service;

import com.controlefreelancer.api.domain.model.SettingsModel;
import com.controlefreelancer.api.dto.SettingsDto;

public interface SettingsService {

    SettingsModel getSettings();
    
    SettingsModel update(SettingsDto settings);

}
