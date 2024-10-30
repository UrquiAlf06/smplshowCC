package com.au.usermgmt;

import com.au.usermgmt.exceptions.ResourceNotFoundException;
import com.au.usermgmt.model.Role;
import com.au.usermgmt.model.User;
import com.au.usermgmt.repository.RoleRepository;
import com.au.usermgmt.repository.UserRepository;
import com.au.usermgmt.request.CreateUserRequest;
import com.au.usermgmt.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("Alfonso");
        user.setLastName("Urquia");
        user.setEmail("alfonso.urquia@simpleshow.com");
        user.setPassword("dummypassword");
    }

    @Test
    void testGetUserByIdSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User userFound = userService.getUserById(1L);
        assertEquals(user, userFound);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(2L);
        });
    }

    /*
    * Unit test for getting users for their names
    * */

}
