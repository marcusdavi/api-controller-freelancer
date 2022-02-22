package com.controlefreelancer.api.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.controlefreelancer.api.dto.UserDto;
import com.controlefreelancer.api.dto.response.UserResponseDto;

public interface UserService {

    UserResponseDto update(Integer id, @Valid UserDto user);

    UserResponseDto create(@Valid UserDto user);

    List<UserResponseDto> findByName(String name);

    Optional<UserResponseDto> get(Integer id);

    List<UserResponseDto> list();

}
