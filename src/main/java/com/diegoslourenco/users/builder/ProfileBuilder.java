package com.diegoslourenco.users.builder;

import com.diegoslourenco.users.dto.ProfileDTO;
import com.diegoslourenco.users.model.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileBuilder {

    public Profile build(ProfileDTO dto) {
        Profile profile = new Profile();
        profile.setName(dto.getName());
        return profile;
    }

}
