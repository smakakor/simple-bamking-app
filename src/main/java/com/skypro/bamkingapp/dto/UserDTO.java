package com.skypro.bamkingapp.dto;

import com.skypro.bamkingapp.moodle.Account;
import com.skypro.bamkingapp.moodle.User;

import java.util.List;
import java.util.stream.Collectors;

public record UserDTO(String username, String firstName, String lastName, List<String> accounts) {

    public static UserDTO fromUser(User user) {
        return new UserDTO(user.getUsername(), user.getFirstName(), user.getLastName(),
                user.getAccounts().stream().map(Account::getAccountNumber).collect(Collectors.toList()));
    }
}
