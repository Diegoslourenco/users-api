package com.diegoslourenco.users.builder;

import com.diegoslourenco.users.dto.UserDTO;
import com.diegoslourenco.users.model.Profile;
import com.diegoslourenco.users.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserBuilder {

    public  User build(UserDTO dto, Profile profile) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setProfile(profile);
        return user;
    }
}
