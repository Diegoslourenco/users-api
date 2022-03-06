package com.diegoslourenco.users.service;

import com.diegoslourenco.users.builder.UserDTOBuilder;
import com.diegoslourenco.users.dto.UserDTO;
import com.diegoslourenco.users.model.User;
import com.diegoslourenco.users.repository.UserRepository;
import com.diegoslourenco.users.utils.MockUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private UserDTOBuilder userDTOBuilder;

    @Test
    public void getAllForEmptyListTest() {

        // Given
        List<User> userMock = new ArrayList<>();
        List<UserDTO> expected = new ArrayList<>();

        // When
        when(userRepository.findAll()).thenReturn(userMock);

        // Then
        List<UserDTO> result = userService.getAll();

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void getAllForListWithOneProfileDTOTest() throws IOException {

        // Given
        List<User> userMock = MockUtils.mockUserListWithOneObject();
        List<UserDTO> expected = MockUtils.mockUserDTOList("src/test/resources/json/user/response/userDTOListWithOneObject.json");

        // When
        when(userRepository.findAll()).thenReturn(userMock);

        // Then
        List<UserDTO> result = userService.getAll();

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void getAllForListWithTwoProfileDTOTest() throws IOException {

        // Given
        List<User> userMock = MockUtils.mockUserListWithTwoObjects();
        List<UserDTO> expected = MockUtils.mockUserDTOList("src/test/resources/json/user/response/userDTOListWithTwoObjects.json");

        // When
        when(userRepository.findAll()).thenReturn(userMock);

        // Then
        List<UserDTO> result = userService.getAll();

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}