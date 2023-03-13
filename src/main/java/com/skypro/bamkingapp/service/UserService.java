package com.skypro.bamkingapp.service;

import com.skypro.bamkingapp.dto.UserDTO;
import com.skypro.bamkingapp.dto.request.CreateUserRequest;
import com.skypro.bamkingapp.exception.InvalidPasswordException;
import com.skypro.bamkingapp.exception.UserAlreadyExistsException;
import com.skypro.bamkingapp.exception.UserNotFoundException;
import com.skypro.bamkingapp.moodle.Currency;
import com.skypro.bamkingapp.moodle.User;
import com.skypro.bamkingapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountService accountService;

    public UserService(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    public UserDTO addUser(CreateUserRequest request) {

        if (userRepository.findById(request.username()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        validateUser(request);
        User user = userRepository.save(request.toUser());
        createNewUserAccounts(user);
        return UserDTO.fromUser(user);
    }

    private void validateUser(CreateUserRequest request) {
    }

    public void updatePassword(String username, String password, String newPassword) {
        User user = userRepository.findById(username).orElseThrow(UserNotFoundException::new);
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException();
        }
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public void removeUser(String username) {
        User user = userRepository.findById(username).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    public User getUser(String username) {
        return userRepository.findById(username).orElseThrow(UserNotFoundException::new);
    }

    public Collection<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserDTO::fromUser).collect(Collectors.toList());
    }

    private User createNewUserAccounts(User user) {
        user.addAccount(accountService.createAccount(user, Currency.RUB));
        user.addAccount(accountService.createAccount(user, Currency.USD));
        user.addAccount(accountService.createAccount(user, Currency.EUR));
        return user;
    }
}

