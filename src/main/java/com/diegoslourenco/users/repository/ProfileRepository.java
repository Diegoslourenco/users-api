package com.diegoslourenco.users.repository;

import com.diegoslourenco.users.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
