package com.skypro.bamkingapp.dto;

import com.skypro.bamkingapp.moodle.Account;
import com.skypro.bamkingapp.moodle.Currency;

public record AccountDTO(String accountNumber, Currency currency, double amount) {
    public static AccountDTO fromAccount(Account account) {
        return new AccountDTO(account.getAccountNumber(), account.getCurrency(), account.getBalance());
    }
}
