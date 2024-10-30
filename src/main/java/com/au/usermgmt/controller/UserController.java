package com.au.usermgmt.controller;

import com.au.usermgmt.dto.UserDto;
import com.au.usermgmt.exceptions.AlreadyExistsException;
import com.au.usermgmt.exceptions.ResourceNotFoundException;
import com.au.usermgmt.model.User;
import com.au.usermgmt.request.CreateUserRequest;
import com.au.usermgmt.response.ApiResponse;
import com.au.usermgmt.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("/{userId}/getById")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User by Id: " +userId , userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody CreateUserRequest request, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse(bindingResult.getAllErrors().get(0).getDefaultMessage(), null));
        }
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User created!", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/getAllByTheirName")
    public ResponseEntity<ApiResponse> getUsersByFirstName(@RequestParam String firstName){
        /*
        * Check here for the authentication of the user if it has the right role
        * */
        try {
            List<User> users = userService.getUsersByName(firstName);
            List<UserDto> convertedUserToDto = userService.getConvertedUsers(users);
            return ResponseEntity.ok(new ApiResponse("Users by first name containing: " +firstName, convertedUserToDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        /*
        *catch the exception where the status should be unauthorized of the user if it does not possess the admin role
        * */
    }
}