package com.service.rest.users;

import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserData> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{user_id}")
    public UserData getUserById(@PathVariable("user_id") Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    public UserData getUserById(@RequestBody UserData userData) {
        return userRepository.save(userData);
    }

    @DeleteMapping("/{user_id}")
    public void deleteUserById(@PathVariable("user_id") Long userId) {
        userRepository.deleteById(userId);
    }

}
