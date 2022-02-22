package com.controlefreelancer.api.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.controlefreelancer.api.dto.UserDto;
import com.controlefreelancer.api.dto.response.UserResponseDto;
import com.controlefreelancer.api.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    @Mock
    private ApplicationEventPublisher publisher;

    @Test
    public void testListOk() {

	List<UserResponseDto> users = new ArrayList<>();
	users.add(buildUserResponseDto(1, "user 01", "user 01 - test"));
	users.add(buildUserResponseDto(2, "user 02", "user 02 - test"));

	when(service.list()).thenReturn(users);

	List<UserResponseDto> response = controller.list().getBody();

	assertTrue(!response.isEmpty());
	assertEquals(2, response.size());
	assertEquals(1, response.get(0).getId());
	assertEquals("user 01", response.get(0).getName());
	assertEquals(2, response.get(1).getId());
	assertEquals("user 02", response.get(1).getName());

    }

    @Test
    public void testListEmptyOk() {

	when(service.list()).thenReturn(new ArrayList<>());

	List<UserResponseDto> response = controller.list().getBody();

	assertTrue(response.isEmpty());

    }

    @Test
    public void testGetUserOk() {

	when(service.get(Mockito.anyInt())).thenReturn(Optional.of(buildUserResponseDto(1, "Marcus", "marcus@gmail.com")));

	ResponseEntity<UserResponseDto> response = controller.get(1);

	assertNotNull(response.getBody());
	assertEquals(1, response.getBody().getId());
	assertEquals("Marcus", response.getBody().getName());
	assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testGetUsernotFound() {

	when(service.get(Mockito.anyInt())).thenReturn(Optional.empty());

	ResponseEntity<UserResponseDto> response = controller.get(1);

	assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	assertNull(response.getBody());

    }

    @Test
    public void testGetUserByName() {

	List<UserResponseDto> users = new ArrayList<>();
	users.add(buildUserResponseDto(1, "Marcus", "marcus@gmail.com"));
	users.add(buildUserResponseDto(2, "Marcio", "marcio@gmail.com"));

	when(service.findByName(Mockito.anyString())).thenReturn(users);

	List<UserResponseDto> response = controller.findByName("MAR").getBody();

	assertTrue(!response.isEmpty());
	assertEquals(2, response.size());
	assertEquals(1, response.get(0).getId());
	assertEquals("Marcus", response.get(0).getName());
	assertEquals(2, response.get(1).getId());
	assertEquals("Marcio", response.get(1).getName());

    }

    @Test
    public void testCreateUserOk() {

	UserDto userDto = buildUserDto("User 01 SA", "User 01");
	UserResponseDto user = buildUserResponseDto(1, userDto.getName(), userDto.getEmail());

	when(service.create(Mockito.any())).thenReturn(user);

	ResponseEntity<UserResponseDto> response = controller.create(userDto, null);

	assertNotNull(response.getBody());
	assertEquals(1, response.getBody().getId());
	assertEquals("User 01 SA", response.getBody().getName());
	assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateOk() {

	UserDto userDto = buildUserDto("User 01 SA", "User 01");
	UserResponseDto user = buildUserResponseDto(1, userDto.getName(), userDto.getEmail());

	when(service.update(Mockito.anyInt(), Mockito.any())).thenReturn(user);

	ResponseEntity<UserResponseDto> response = controller.update(1, userDto);

	assertNotNull(response.getBody());
	assertEquals(1, response.getBody().getId());
	assertEquals("User 01 SA", response.getBody().getName());
	assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private UserResponseDto buildUserResponseDto(Integer id, String name, String email) {
	return UserResponseDto.builder().id(id).name(name).email(email).build();
    }

    private UserDto buildUserDto(String name, String email) {
	return UserDto.builder().name(name).email(email).build();
    }
}
