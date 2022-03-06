package com.diegoslourenco.users.controller;

import com.diegoslourenco.users.dto.UserDTO;
import com.diegoslourenco.users.exceptionHandler.EmailNotUniqueException;
import com.diegoslourenco.users.exceptionHandler.NameNotUniqueException;
import com.diegoslourenco.users.repository.ProfileRepository;
import com.diegoslourenco.users.service.ProfileService;
import com.diegoslourenco.users.service.UserService;
import com.diegoslourenco.users.utils.MockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @MockBean(answer = Answers.CALLS_REAL_METHODS)
    private ProfileService profileService;

    @MockBean
    private ProfileRepository profileRepository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void  getAllTest() throws Exception {

        // Given
        String uri = "/users";
        String expected = "[]";

        // When
        when(userService.getAll()).thenReturn(new ArrayList<>());

        // Then
        MvcResult mvcResult = mockMvc
                .perform(get(uri).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void  getByIdSuccessTest() throws Exception {

        // Given
        String uri = "/users/1";

        UserDTO UserMocked = MockUtils.mockUserDTO();

        String expected = MockUtils.mockString("src/test/resources/json/User/response/userDTO.json");

        // When
        when(userService.getOne(1L)).thenReturn(UserMocked);

        // Then
        MvcResult mvcResult = mockMvc
                .perform(get(uri).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.OK.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

    @Test
    public void  getByIdNotFoundTest() throws Exception {

        // Given
        String uri = "/users/1";

        String expected = MockUtils.mockString("src/test/resources/json/user/error/userNotFound.json");

        // When
        when(userService.getOne(1L)).thenThrow(new EmptyResultDataAccessException(1));

        // Then
        MvcResult mvcResult = mockMvc
                .perform(get(uri).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

    @Test
    public void  createSuccessTest() throws Exception {

        // Given
        String uri = "/users";

        String profileMocked = MockUtils.mockString("src/test/resources/json/user/request/userDTORequest.json");

        String expected = "1";

        // When
        when(userService.save(any())).thenReturn(1L);

        // Then
        MvcResult mvcResult = mockMvc
                .perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(profileMocked))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.CREATED.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

    @Test
    public void  createNotFoundTest() throws Exception {

        // Given
        String uri = "/users";

        String userMocked = MockUtils.mockString("src/test/resources/json/user/request/userDTORequest.json");

        String expected = MockUtils.mockString("src/test/resources/json/user/error/userNotFound.json");

        // When
        when(profileRepository.findById(any())).thenReturn(Optional.empty());
        when(userService.save(any())).thenThrow(new EmptyResultDataAccessException(1));

        // Then
        MvcResult mvcResult = mockMvc
                .perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(userMocked))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

    @Test
    public void  createBadRequestNotUniqueNameTest() throws Exception {

        // Given
        String uri = "/users";

        String userMocked = MockUtils.mockString("src/test/resources/json/user/request/userDTORequest.json");

        String expected = MockUtils.mockString("src/test/resources/json/user/error/userNameNotUnique.json");

        // When
        when(userService.save(any())).thenThrow(NameNotUniqueException.class);

        // Then
        MvcResult mvcResult = mockMvc
                .perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(userMocked))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

    @Test
    public void  createBadRequestNotUniqueEmailTest() throws Exception {

        // Given
        String uri = "/users";

        String userMocked = MockUtils.mockString("src/test/resources/json/user/request/userDTORequest.json");

        String expected = MockUtils.mockString("src/test/resources/json/user/error/userEmailNotUnique.json");

        // When
        when(userService.save(any())).thenThrow(EmailNotUniqueException.class);

        // Then
        MvcResult mvcResult = mockMvc
                .perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(userMocked))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

}