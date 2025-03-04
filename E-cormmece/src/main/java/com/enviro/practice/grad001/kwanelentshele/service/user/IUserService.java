package com.enviro.practice.grad001.kwanelentshele.service.user;

import com.enviro.practice.grad001.kwanelentshele.dto.UserDto;
import com.enviro.practice.grad001.kwanelentshele.model.User;
import com.enviro.practice.grad001.kwanelentshele.request.CreateUserRequest;
import com.enviro.practice.grad001.kwanelentshele.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User creatUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest updateRequest, Long userId);
    void deleteUser(Long userId);
    UserDto convertToDto(User user);

}
