package com.au.usermgmt.service;

import com.au.usermgmt.dto.UserDto;
import com.au.usermgmt.model.User;
import com.au.usermgmt.request.CreateUserRequest;

import java.util.List;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    List<User> getUsersByName(String firstName);

    List<UserDto> getConvertedUsers(List<User> users);
    UserDto convertUserToDto(User user);
}
