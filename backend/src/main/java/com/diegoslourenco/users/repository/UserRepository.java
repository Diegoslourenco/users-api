package com.diegoslourenco.users.repository;

import com.diegoslourenco.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getByName(String name);

    Optional<User> getByEmail(String email);

    List<User> getByProfile(Long id);

}
