package com.diegoslourenco.users.service;

import com.diegoslourenco.users.builder.UserBuilder;
import com.diegoslourenco.users.builder.UserDTOBuilder;
import com.diegoslourenco.users.dto.ProfileDTO;
import com.diegoslourenco.users.dto.UserDTO;
import com.diegoslourenco.users.exceptionHandler.EmailNotUniqueException;
import com.diegoslourenco.users.exceptionHandler.NameNotUniqueException;
import com.diegoslourenco.users.exceptionHandler.ProfileNotFoundException;
import com.diegoslourenco.users.exceptionHandler.UserNotFoundException;
import com.diegoslourenco.users.model.Profile;
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
import java.util.Optional;

import static com.diegoslourenco.users.utils.MockUtils.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private UserDTOBuilder userDTOBuilder;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private UserBuilder userBuilder;

    @Mock
    private ProfileService profileService;

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
    public void getAllForListWithOneUserDTOTest() throws IOException {

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
    public void getAllForListWithTwoUserDTOTest() throws IOException {

        // Given
        List<User> userMock = MockUtils.mockUserListWithTwoObjects();
        List<UserDTO> expected = MockUtils.mockUserDTOList("src/test/resources/json/user/response/userDTOListWithTwoObjects.json");

        // When
        when(userRepository.findAll()).thenReturn(userMock);

        // Then
        List<UserDTO> result = userService.getAll();

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void getByIdTest() {

        // Given
        Profile profileMocked = MockUtils.mockProfile(PROFILE_ID_1, PROFILE_NAME_ADMIN);
        User userMocked = MockUtils.mockUser(PROFILE_ID_1, USER_NAME_DIEGO, USER_EMAIL_DIEGO, profileMocked);
        UserDTO expected = MockUtils.mockUserDTO();

        // When
        when(userRepository.findById(1L)).thenReturn(Optional.of(userMocked));

        // Then
        UserDTO result = userService.getOne(1L);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test(expected = UserNotFoundException.class)
    public void getByIdNotFoundTest() {

        // When
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Then
        UserDTO result = userService.getOne(1L);
    }

    @Test
    public void createTest() {

        // Given
        UserDTO userDTO = MockUtils.mockUserDTO();
        Profile profileMocked = MockUtils.mockProfile(1L, PROFILE_NAME_ADMIN);
        User userMocked = mockUser(1L, USER_NAME_DIEGO, USER_EMAIL_DIEGO, profileMocked);

        // When
        when(profileService.getById(any())).thenReturn(profileMocked);
        when(userRepository.getByName(userDTO.getName())).thenReturn(Optional.empty());
        when(userRepository.getByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(userMocked);

        // Then
        Long result = userService.save(userDTO);
        assertThat(result).isEqualTo(1L);
    }

    @Test(expected = ProfileNotFoundException.class)
    public void createForNotFoundProfileTest() {

        // Given
        UserDTO userDTO = MockUtils.mockUserDTO();
        Profile profileMocked = MockUtils.mockProfile(1L, PROFILE_NAME_ADMIN);
        User userMocked = mockUser(1L, USER_NAME_DIEGO, USER_EMAIL_DIEGO, profileMocked);

        // When
        when(profileService.getById(any())).thenThrow(new ProfileNotFoundException(1));

        // Then
        Long result = userService.save(userDTO);
    }

    @Test(expected = NameNotUniqueException.class)
    public void createForNotUniqueNameTest() {

        // Given
        UserDTO userDTO = MockUtils.mockUserDTO();
        Profile profileMocked = MockUtils.mockProfile(1L, PROFILE_NAME_ADMIN);
        User userMocked = mockUser(1L, USER_NAME_DIEGO, USER_EMAIL_DIEGO, profileMocked);

        // When
        when(profileService.getById(any())).thenReturn(profileMocked);
        when(userRepository.getByName(userDTO.getName())).thenReturn(Optional.of(userMocked));

        // Then
        Long result = userService.save(userDTO);
    }

    @Test(expected = EmailNotUniqueException.class)
    public void createForNotUniqueEmailTest() {

        // Given
        UserDTO userDTO = MockUtils.mockUserDTO();
        Profile profileMocked = MockUtils.mockProfile(1L, PROFILE_NAME_ADMIN);
        User userMocked = mockUser(1L, USER_NAME_DIEGO, USER_EMAIL_DIEGO, profileMocked);

        // When
        when(profileService.getById(any())).thenReturn(profileMocked);
        when(userRepository.getByName(userDTO.getName())).thenReturn(Optional.empty());
        when(userRepository.getByEmail(userDTO.getEmail())).thenReturn(Optional.of(userMocked));

        // Then
        Long result = userService.save(userDTO);
    }

    @Test
    public void updateTest() {

        // Given
        UserDTO userDTO = MockUtils.mockUserDTO();
        Profile profileMocked = MockUtils.mockProfile(1L, PROFILE_NAME_ADMIN);
        User userMocked = mockUser(1L, USER_NAME_DIEGO, USER_EMAIL_DIEGO, profileMocked);

        // When
        when(profileService.getById(any())).thenReturn(profileMocked);
        when(userRepository.findById(any())).thenReturn(Optional.of(userMocked));
        when(userRepository.save(any())).thenReturn(userMocked);

        // Then
        UserDTO result = userService.update(1L, userDTO);
        assertThat(result).usingRecursiveComparison().isEqualTo(userDTO);
    }

    @Test(expected = ProfileNotFoundException.class)
    public void updateForProfileNotFoundExceptionTest() {

        // Given
        UserDTO userDTO = MockUtils.mockUserDTO();
        Profile profileMocked = MockUtils.mockProfile(1L, PROFILE_NAME_ADMIN);
        User userMocked = mockUser(1L, USER_NAME_BOB, USER_EMAIL_DIEGO, profileMocked);

        // When
        when(userRepository.findById(any())).thenReturn(Optional.of(userMocked));
        when(profileService.getById(any())).thenThrow(new ProfileNotFoundException(1));

        // Then
        UserDTO result = userService.update(1L, userDTO);
    }

    @Test(expected = NameNotUniqueException.class)
    public void updateForNameNotUniqueTest() {

        // Given
        UserDTO userDTO = MockUtils.mockUserDTO();
        Profile profileMocked = MockUtils.mockProfile(1L, PROFILE_NAME_ADMIN);
        User userMocked = mockUser(1L, USER_NAME_BOB, USER_EMAIL_DIEGO, profileMocked);

        // When
        when(userRepository.findById(any())).thenReturn(Optional.of(userMocked));
        when(profileService.getById(any())).thenReturn(profileMocked);
        when(userRepository.getByName(userDTO.getName())).thenReturn(Optional.of(userMocked));

        // Then
        UserDTO result = userService.update(1L, userDTO);
    }

    @Test(expected = EmailNotUniqueException.class)
    public void updateForEmailNotUniqueTest() {

        // Given
        UserDTO userDTO = MockUtils.mockUserDTO();
        Profile profileMocked = MockUtils.mockProfile(1L, PROFILE_NAME_ADMIN);
        User userMocked = mockUser(1L, USER_NAME_DIEGO, USER_EMAIL_BOB, profileMocked);

        // When
        when(userRepository.findById(any())).thenReturn(Optional.of(userMocked));
        when(profileService.getById(any())).thenReturn(profileMocked);
        when(userRepository.getByEmail(userDTO.getEmail())).thenReturn(Optional.of(userMocked));

        // Then
        UserDTO result = userService.update(1L, userDTO);
    }

    @Test(expected = UserNotFoundException.class)
    public void updateForUserNotFoundExceptionTest() {

        // Given
        UserDTO userDTO = MockUtils.mockUserDTO();
        Profile profileMocked = MockUtils.mockProfile(1L, PROFILE_NAME_ADMIN);

        // When
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // Then
        UserDTO result = userService.update(1L, userDTO);
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteTest() {

        // When
        doThrow(new UserNotFoundException(1)).when(userRepository).deleteById(any());

        // Then
        userService.delete(1L);
    }

}