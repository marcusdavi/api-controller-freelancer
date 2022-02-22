package com.controlefreelancer.api.dto.response;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.controlefreelancer.api.domain.model.UserModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@AllArgsConstructor
public class UserResponseDto {

    private Integer id;
    
    private String name;

    private String email;

    private String cnpj;

    private String companyName;

    private String phoneNumber;

 // @formatter:off
    public static UserResponseDto modelToResponse(UserModel user) {
	return UserResponseDto.builder()
		.id(user.getId())
		.cnpj(user.getCnpj())
		.companyName(user.getCompanyName())
		.email(user.getEmail())
		.name(user.getName())
		.phoneNumber(user.getPhoneNumber())
		.build();
    }


    public static List<UserResponseDto> listModelToResponse(List<UserModel> users) {
	return users.stream()
		.map(UserResponseDto::modelToResponse)
		.collect(Collectors.toList());
    }
    // @formatter:on

    public static Optional<UserResponseDto> optionalToResponse(Optional<UserModel> user) {
	return user.map(UserResponseDto::modelToResponse);

    }

}
