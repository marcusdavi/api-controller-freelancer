package com.controlefreelancer.api.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.controlefreelancer.api.domain.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    Optional<UserModel> findByEmail(String username);

    List<UserModel> findByNameContainsIgnoreCase(String name);

}
