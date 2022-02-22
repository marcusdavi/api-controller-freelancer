package com.controlefreelancer.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.controlefreelancer.api.domain.model.SettingsModel;

public interface SettingsRepository extends JpaRepository<SettingsModel, Integer> {

}
