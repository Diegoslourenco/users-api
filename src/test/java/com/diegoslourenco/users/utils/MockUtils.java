package com.diegoslourenco.users.utils;

import com.diegoslourenco.users.dto.ProfileDTO;
import com.diegoslourenco.users.model.Profile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MockUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static List<ProfileDTO> mockProfileDTOList(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), new TypeReference<List<ProfileDTO>>(){});
    }

    public static List<Profile> mockProfileListWithOneObject() {

        List<Profile> profiles = new ArrayList<>();
        profiles.add(mockProfile(1L, "admin"));

        return profiles;
    }

    public static List<Profile> mockProfileListWithTwoObjects() {

        List<Profile> profiles = new ArrayList<>();
        profiles.add(mockProfile(1L, "admin"));
        profiles.add(mockProfile(2L, "basic_user"));

        return profiles;
    }

    private static Profile mockProfile(Long id, String name) {
        Profile profile = new Profile();
        profile.setId(id);
        profile.setName(name);
        return profile;
    }
}

