package com.enviro.practice.grad001.kwanelentshele.data;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.enviro.practice.grad001.kwanelentshele.model.User;
import com.enviro.practice.grad001.kwanelentshele.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
      createDefaultUserIfNotExist();
    }

    private void createDefaultUserIfNotExist(){
        for(int createUser = 1; createUser<3; createUser++){
            String defaultEmail = "User" + createUser + "@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("User");
            user.setLastName("App");
            user.setEmail(defaultEmail);
            user.setPassword("user");
            userRepository.save(user);
            System.out.println("Default user " + createUser + " created successfully!");
        }
    }  
}
