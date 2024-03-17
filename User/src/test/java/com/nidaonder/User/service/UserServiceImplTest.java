package com.nidaonder.User.service;

import com.nidaonder.User.core.exception.BusinessException;
import com.nidaonder.User.core.exception.ItemExistException;
import com.nidaonder.User.core.exception.ItemNotFoundException;
import com.nidaonder.User.dao.UserRepository;
import com.nidaonder.User.dto.request.UserSaveRequest;
import com.nidaonder.User.dto.request.UserUpdatePasswordRequest;
import com.nidaonder.User.dto.request.UserUpdateRequest;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void shouldReturnAllUsers() {
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

        UserResponse response1 = new UserResponse(
                1L,
                "test1",
                "surname1",
                "testuser1@gmail.com",
                Status.ACTIVE,
                LocalDate.of(1996, 6, 6),
                10.01,
                15.01);
        UserResponse response2 = new UserResponse(
                2L,
                "test2",
                "surname2",
                "testuser2@gmail.com",
                Status.ACTIVE,
                LocalDate.of(1999, 9, 9),
                10.01,
                15.01);
        List<UserResponse> expectedResponses = Arrays.asList(response1, response2);

        when(userMapper.entityToListResponse(users)).thenReturn(expectedResponses);

        List<UserResponse> actualResponses = userService.findAll();

        assertEquals(expectedResponses, actualResponses);
        verify(userRepository).findAll();
        verify(userMapper).entityToListResponse(users);
    }

    @Test
    void shouldReturnUserResponseWhenUserIdIsFound() {
        User user1= new User();
        user1.setName("test1");
        user1.setSurname("surname1");
        user1.setEmail("testuser1@gmail.com");
        user1.setPassword("123123");
        user1.setStatus(Status.ACTIVE);
        user1.setBirthDate(LocalDate.of(1996, 6, 6));
        user1.setLatitude(10.01);
        user1.setLongitude(15.01);
        UserResponse response1 = new UserResponse(
                1L,
                "test1",
                "surname1",
                "testuser1@gmail.com",
                Status.ACTIVE,
                LocalDate.of(1996, 6, 6),
                10.01,
                15.01);

        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

        when(userMapper.entityToResponse(user1)).thenReturn(response1);

        UserResponse foundUser = userService.findById(user1.getId());

        assertEquals(response1, foundUser);
        verify(userRepository).findById(user1.getId());
        verify(userMapper).entityToResponse(user1);
    }

    @Test
    void shouldThrowExceptionWhenUserIdIsNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class, () -> userService.findById(userId));
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        UserSaveRequest request1 = new UserSaveRequest(
                "name1",
                "surname1",
                "request@gmail.com",
                "requestPass",
                LocalDate.of(1996,1,1),
                10.00,
                11.00);

        when(userRepository.findByEmail(request1.email())).thenReturn(Optional.of(new User()));

        assertThrows(ItemExistException.class, () -> userService.save(request1));
        verify(userRepository).findByEmail(request1.email());
    }

    @Test
    void shouldSaveUserAndReturnUserResponseWhenEmailIsNew() {
        UserSaveRequest request1 = new UserSaveRequest(
                "name1",
                "surname1",
                "request@gmail.com",
                "requestPass",
                LocalDate.of(1996,1,1),
                10.00,
                11.00);

        User savedUser = new User();
        savedUser.setEmail(request1.email());
        UserResponse expectedResponse = new UserResponse(
                1L,
                request1.name(),
                request1.surname(),
                request1.email(),
                Status.ACTIVE,
                request1.birthDate(),
                request1.latitude(),
                request1.longitude());

        when(userRepository.findByEmail(request1.email())).thenReturn(Optional.empty());
        when(userMapper.requestToEntity(request1)).thenReturn(savedUser);
        when(userMapper.entityToResponse(savedUser)).thenReturn(expectedResponse);

        UserResponse actualResponse = userService.save(request1);

        assertEquals(expectedResponse, actualResponse);
        verify(userRepository).findByEmail(request1.email());
        verify(userRepository).save(savedUser);
        verify(userMapper).requestToEntity(request1);
        verify(userMapper).entityToResponse(savedUser);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        Long userId = 1L;
        UserUpdateRequest request1 = new UserUpdateRequest(
                "name1",
                "surname1",
                "request@gmail.com",
                Status.INACTIVE,
                LocalDate.of(1996,1,1),
                10.00,
                11.00);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class, () -> userService.update(userId, request1));
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenEmailIsUsedByAnotherUser(){
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(5L);
        UserUpdateRequest request1 = new UserUpdateRequest(
                "name1",
                "surname1",
                "request@gmail.com",
                Status.INACTIVE,
                LocalDate.of(1996,1,1),
                10.00,
                11.00);
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(userRepository.findByEmail(request1.email())).thenReturn(Optional.of(existingUser));

        assertThrows(ItemExistException.class, () -> userService.update(userId, request1));
        verify(userRepository).findById(userId);
        verify(userRepository).findByEmail(request1.email());
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        UserUpdateRequest request1 = new UserUpdateRequest(
                "name1",
                "surname1",
                "request@gmail.com",
                Status.ACTIVE,
                LocalDate.of(1996,1,1),
                10.00,
                11.00);
        UserResponse expectedResponse = new UserResponse(
                userId,
                request1.name(),
                request1.surname(),
                request1.email(),
                request1.status(),
                request1.birthDate(),
                request1.latitude(),
                request1.longitude()
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(request1.email())).thenReturn(Optional.empty());
        when(userMapper.entityToResponse(user)).thenReturn(expectedResponse);

        UserResponse actualResponse = userService.update(userId, request1);

        assertEquals(expectedResponse, actualResponse);
        verify(userRepository).findById(userId);
        verify(userRepository).findByEmail(request1.email());
        verify(userRepository).save(user);
        verify(userMapper).update(user, request1);
        verify(userMapper).entityToResponse(user);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnPasswordUpdate() {
        Long userId = 1L;
        UserUpdatePasswordRequest request = new UserUpdatePasswordRequest(
                "oldPassword",
                "newPassword",
                "newPasswordVerify");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> userService.updatePassword(userId, request));

        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenCurrentPasswordWrongOnPasswordUpdate() {
        Long userId = 1L;
        UserUpdatePasswordRequest request = new UserUpdatePasswordRequest(
                "wrongOldPassword",
                "newPassword",
                "newPassword");
        User user = new User();
        user.setPassword("correctOldPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(BusinessException.class, () -> userService.updatePassword(userId, request));

        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenCurrentPasswordSameOldOnPasswordUpdate() {
        Long userId = 1L;
        UserUpdatePasswordRequest request = new UserUpdatePasswordRequest(
                "oldPassword",
                "oldPassword",
                "oldPassword");
        User user = new User();
        user.setPassword("oldPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(BusinessException.class, () -> userService.updatePassword(userId, request));

        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenPasswordsDoNotMatchOnPasswordUpdate() {
        Long userId = 1L;
        UserUpdatePasswordRequest request = new UserUpdatePasswordRequest(
                "oldPassword",
                "newPassword",
                "differentPassword");
        User user = new User();
        user.setPassword("oldPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(BusinessException.class, () -> userService.updatePassword(userId, request));

        verify(userRepository).findById(userId);
    }

    @Test
    void shouldUpdatePassword() {
        Long userId = 1L;
        UserUpdatePasswordRequest request = new UserUpdatePasswordRequest(
                "oldPassword",
                "newPassword",
                "newPassword");
        User user = new User();
        user.setId(1L);
        user.setPassword("oldPassword");
        UserResponse expectedResponse = new UserResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getStatus(),
                user.getBirthDate(),
                user.getLatitude(),
                user.getLongitude()
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.entityToResponse(user)).thenReturn(expectedResponse);

        UserResponse actualResponse = userService.updatePassword(userId, request);

        assertEquals(expectedResponse, actualResponse);
        verify(userRepository).findById(userId);
        verify(userRepository).save(user);
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExist() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> userService.deleteById(userId));

        verify(userRepository).findById(userId);
    }

    @Test
    void shouldDeleteUserWhenIdExists() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteById(userId);

        verify(userRepository).findById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void checkUserExistsShouldThrowExceptionWhenUserDoesNotExist() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> userService.checkUserExists(userId));

        verify(userRepository).findById(userId);
    }

    @Test
    void checkUserExistsWhenUserExists() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.checkUserExists(userId);

        verify(userRepository).findById(userId);
    }
}