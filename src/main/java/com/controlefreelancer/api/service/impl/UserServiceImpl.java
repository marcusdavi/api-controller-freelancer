package com.controlefreelancer.api.service.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.controlefreelancer.api.domain.model.UserModel;
import com.controlefreelancer.api.domain.repository.UserRepository;
import com.controlefreelancer.api.dto.UserDto;
import com.controlefreelancer.api.dto.response.UserResponseDto;
import com.controlefreelancer.api.exception.ResourceNotFoundException;
import com.controlefreelancer.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public UserServiceImpl(MessageSource messageSource, UserRepository userRepository) {
	this.messageSource = messageSource;
	this.userRepository = userRepository;
    }

    public List<UserResponseDto> findByName(String name) {
	return UserResponseDto.listModelToResponse(userRepository.findByNameContainsIgnoreCase(name));
    }

    public List<UserResponseDto> list() {
	return UserResponseDto.listModelToResponse(userRepository.findAll());
    }

    public Optional<UserResponseDto> get(Integer id) {
	return UserResponseDto.optionalToResponse(userRepository.findById(id));
    }

    public UserResponseDto create(UserDto user) {

	UserModel userModel = new UserModel();

	BeanUtils.copyProperties(user, userModel);
	userModel.setPassword(encryptPassword(user.getPassword()));

	return UserResponseDto.modelToResponse(userRepository.save(userModel));

    }

    private UserModel validExistenceUser(Integer id) {
	return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
		messageSource.getMessage("user-not-found", null, LocaleContextHolder.getLocale())));
    }

    public UserResponseDto update(Integer id, @Valid UserDto user) {

	UserModel userUpdate = validExistenceUser(id);
	BeanUtils.copyProperties(user, userUpdate);
	userUpdate.setPassword(encryptPassword(user.getPassword()));
	return UserResponseDto.modelToResponse(userRepository.save(userUpdate));
    }

    private String encryptPassword(String password) {
	BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();
	return enconder.encode(password);
    }

}
