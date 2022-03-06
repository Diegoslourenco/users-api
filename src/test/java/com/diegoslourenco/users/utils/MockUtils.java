package com.diegoslourenco.users.utils;

import com.diegoslourenco.users.dto.ProfileDTO;
import com.diegoslourenco.users.dto.UserDTO;
import com.diegoslourenco.users.model.Profile;
import com.diegoslourenco.users.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MockUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String PROFILE_NAME_ADMIN = "admin";
    public static String PROFILE_NAME_BASIC_USER = "basic_user";

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
        profiles.add(mockProfile(1L, PROFILE_NAME_ADMIN));
        profiles.add(mockProfile(2L, PROFILE_NAME_BASIC_USER));

        return profiles;
    }

    public static Profile mockProfile(Long id, String name) {
        Profile profile = new Profile();
        profile.setId(id);
        profile.setName(name);
        return profile;
    }

    public static ProfileDTO mockProfileDTO() {
        return new ProfileDTO(1L, PROFILE_NAME_ADMIN);
    }

    public static String mockString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static List<User> mockUserListWithOneObject() {

        List<User> users = new ArrayList<>();
        users.add(mockUser(1L, "Diego Lourenço", "diego.lourenco15@gmail.com"));

        return users;
    }

    public static List<User> mockUserListWithTwoObjects() {

        List<User> users = new ArrayList<>();
        users.add(mockUser(1L, "Diego Lourenço", "diego.lourenco15@gmail.com"));
        users.add(mockUser(2L, "Bob Dylan", "bob@email.com"));

        return users;
    }

    private static User mockUser(long id, String name, String email) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        return user;
    }

    public static List<UserDTO> mockUserDTOList(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), new TypeReference<List<UserDTO>>(){});
    }


}

