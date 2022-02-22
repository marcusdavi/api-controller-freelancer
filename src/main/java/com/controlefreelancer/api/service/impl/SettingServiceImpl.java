package com.controlefreelancer.api.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.controlefreelancer.api.domain.model.SettingsModel;
import com.controlefreelancer.api.domain.repository.SettingsRepository;
import com.controlefreelancer.api.dto.SettingsDto;
import com.controlefreelancer.api.exception.ResourceNotFoundException;
import com.controlefreelancer.api.service.SettingsService;

@Service
public class SettingServiceImpl implements SettingsService {

    private static final int ID_SETTINGS = 1;
    private final SettingsRepository repository;
    private final MessageSource messageSource;

    public SettingServiceImpl(SettingsRepository repository, MessageSource messageSource) {
	super();
	this.repository = repository;
	this.messageSource = messageSource;
    }

    @Override
    public SettingsModel getSettings() {
	return repository.findById(ID_SETTINGS).orElseThrow(() -> new ResourceNotFoundException(
		messageSource.getMessage("settings-not-added", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public SettingsModel update(SettingsDto settings) {
	SettingsModel settingsUpdate = repository.getOne(ID_SETTINGS);
	BeanUtils.copyProperties(settings, settingsUpdate);
	settingsUpdate.setId(ID_SETTINGS);
	return repository.save(settingsUpdate);
    }

}
