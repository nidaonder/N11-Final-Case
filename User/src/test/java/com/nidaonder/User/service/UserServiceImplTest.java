package com.nidaonder.User.service;

import com.nidaonder.User.dao.UserRepository;
import com.nidaonder.User.dto.response.UserResponse;
import com.nidaonder.User.entity.User;
import com.nidaonder.User.enums.Status;
import com.nidaonder.User.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        User user1= new User();
        user1.setName("test1");
        user1.setSurname("surname1");
        user1.setEmail("testuser1@gmail.com");
        user1.setPassword("123123");
        user1.setStatus(Status.ACTIVE);
        user1.setBirthDate(LocalDate.of(1996, 6, 6));
        user1.setLatitude(10.01);
        user1.setLongitude(15.01);

        User user2= new User();
        user2.setName("test2");
        user2.setSurname("surname2");
        user2.setEmail("testuser2@gmail.com");
        user2.setPassword("123123");
        user2.setStatus(Status.ACTIVE);
        user2.setBirthDate(LocalDate.of(1999, 9, 9));
        user2.setLatitude(10.01);
        user2.setLongitude(15.01);
        List<User> users = new ArrayList<>();

        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        UserResponse response1 = new UserResponse(1L, "test1", "surname1", "testuser1@gmail.com", Status.ACTIVE, LocalDate.of(1996, 6, 6), 10.01, 15.01);
        UserResponse response2 = new UserResponse(2L, "test2", "surname2", "testuser2@gmail.com", Status.ACTIVE, LocalDate.of(1999, 9, 9), 10.01, 15.01);
        List<UserResponse> expectedResponses = Arrays.asList(response1, response2);

        when(userMapper.entityToListResponse(users)).thenReturn(expectedResponses);

        List<UserResponse> actualResponses = userService.findAll();

        assertEquals(expectedResponses, actualResponses);
        verify(userRepository).findAll();
        verify(userMapper).entityToListResponse(users);
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void checkUserExists() {
    }
}