package com.au.usermgmt.service;

import com.au.usermgmt.dto.UserDto;
import com.au.usermgmt.exceptions.AlreadyExistsException;
import com.au.usermgmt.exceptions.ResourceNotFoundException;
import com.au.usermgmt.model.Role;
import com.au.usermgmt.model.User;
import com.au.usermgmt.repository.RoleRepository;
import com.au.usermgmt.repository.UserRepository;
import com.au.usermgmt.request.CreateUserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with Id: " + userId + " is not found!"));
    }

    @Override
    public User createUser(@Valid CreateUserRequest request) {
        if(userExistsByEmail(request.getEmail())){
            throw new AlreadyExistsException(request.getEmail() + " this TOP G already exists");
        }

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role doesnt exists with Id: " +request.getRoleId()));

        return userRepository.save(addUser(request, role));
    }

    private User addUser(CreateUserRequest request, Role role){
        return new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                role
        );
    }

    private boolean userExistsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String firstName) {
        List<User> users = userRepository.findByFirstNameContaining(firstName);
        if(users.isEmpty()) {
            throw new ResourceNotFoundException("No users found with first name containing: " +firstName);
        }
        return users;
    }

    @Override
    public List<UserDto> getConvertedUsers(List<User> users) {
        return users.stream().map(this::convertUserToDto).toList();
    }

    @Override
    public UserDto convertUserToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }
}
