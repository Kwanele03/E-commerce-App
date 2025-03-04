package com.enviro.practice.grad001.kwanelentshele.service.user;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.enviro.practice.grad001.kwanelentshele.dto.UserDto;
import com.enviro.practice.grad001.kwanelentshele.exceptions.AlreadyExistException;
import com.enviro.practice.grad001.kwanelentshele.exceptions.ResourceNotFoundException;
import com.enviro.practice.grad001.kwanelentshele.model.User;
import com.enviro.practice.grad001.kwanelentshele.repository.UserRepository;
import com.enviro.practice.grad001.kwanelentshele.request.CreateUserRequest;
import com.enviro.practice.grad001.kwanelentshele.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
       return userRepository.findById(userId)
       .orElseThrow(() -> new ResourceNotFoundException("User with this id cannot be found!"));
    }

    @Override
    public User creatUser(CreateUserRequest request) {
       return Optional.of(request).filter(user -> !userRepository.existsByEmail(request.getEmail()))
       .map(req -> {
       User user = new User();
       user.setFirstName(request.getFirstName());
       user.setLastName(request.getLastName());
       user.setEmail(request.getEmail());
       user.setPassword(request.getPassword());
       return userRepository.save(user);
       }).orElseThrow(() -> new AlreadyExistException(request.getEmail() + " already exist!"));
    }

    @Override
    public User updateUser(UserUpdateRequest updateRequest, Long userId) {
       return userRepository.findById(userId).map(user -> {
       user.setFirstName(updateRequest.getFirstName());
       user.setLastName(updateRequest.getLastName());
       return userRepository.save(user);
       }).orElseThrow(() -> new ResourceNotFoundException("User to update not found"));
    }

    @Override
    public void deleteUser(Long userId) {
      userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () -> {
        new ResourceNotFoundException("User to delete not found!");

      });
    }

    @Override
    public UserDto convertToDto(User user){
      return modelMapper.map(user, UserDto.class);
    }

}