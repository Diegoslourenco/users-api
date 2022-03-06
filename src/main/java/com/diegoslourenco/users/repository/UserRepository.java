package com.diegoslourenco.users.repository;

import com.diegoslourenco.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
