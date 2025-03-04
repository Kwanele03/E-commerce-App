package com.enviro.practice.grad001.kwanelentshele.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.enviro.practice.grad001.kwanelentshele.dto.UserDto;
import com.enviro.practice.grad001.kwanelentshele.exceptions.AlreadyExistException;
import com.enviro.practice.grad001.kwanelentshele.exceptions.ResourceNotFoundException;
import com.enviro.practice.grad001.kwanelentshele.model.User;
import com.enviro.practice.grad001.kwanelentshele.request.CreateUserRequest;
import com.enviro.practice.grad001.kwanelentshele.request.UserUpdateRequest;
import com.enviro.practice.grad001.kwanelentshele.response.APIResponse;
import com.enviro.practice.grad001.kwanelentshele.service.user.IUserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

                private static final HttpStatusCode NOT_FOUND = HttpStatus.NOT_FOUND;
                protected static final HttpStatusCode CONFLICT = HttpStatus.CONFLICT;
            
                private final IUserService userService;
            
                @GetMapping("/{userId}/users")
                public ResponseEntity<APIResponse> getUserById(@PathVariable Long userId){
                    try{
                        User user = userService.getUserById(userId);
                        UserDto userDto = userService.convertToDto(user);
                        return ResponseEntity.ok(new APIResponse("User returned successfully", userDto));
                    }
                    catch(ResourceNotFoundException exception){
                        return ResponseEntity.status(NOT_FOUND).body(new APIResponse(exception.getMessage(), null));
                    }
                }
            
                @PostMapping("/add")
                public ResponseEntity<APIResponse> createUser(@RequestBody CreateUserRequest request){
                    try {
                        User user = userService.creatUser(request);
                        UserDto userDto = userService.convertToDto(user);
                        return ResponseEntity.ok(new APIResponse("User created successfully!", userDto));
                    } 
                    catch (AlreadyExistException exception) {
                        return ResponseEntity.status(CONFLICT).body(new APIResponse(exception.getMessage(), null));
                    }           
                }

                @PutMapping("/{userId}/update")
                public ResponseEntity<APIResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId){
                    try {
                        User user = userService.updateUser(request, userId);
                        UserDto userDto = userService.convertToDto(user);
                        return ResponseEntity.ok(new APIResponse("User updated successfully!", userDto));
                    } catch (ResourceNotFoundException exception) {
                         return ResponseEntity.status(NOT_FOUND).body(new APIResponse(exception.getMessage(), null));
                    }
                }

                @DeleteMapping("{userId}/delete")
                public ResponseEntity<APIResponse> deleteUser(@PathVariable Long userId){
                   try {
                    userService.deleteUser(userId);
                       return ResponseEntity.ok(new APIResponse("User deleted successfully!", null));
                } catch (ResourceNotFoundException exception) {
                   return ResponseEntity.status(NOT_FOUND).body(new APIResponse(exception.getMessage(), null));
                }
             }
    
    }
