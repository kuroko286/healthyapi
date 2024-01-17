package com.kuroko.heathyapi.feature.account;

import java.util.List;

import com.kuroko.heathyapi.feature.account.payload.AuthResponse;
import com.kuroko.heathyapi.feature.account.payload.LoginRequest;
import com.kuroko.heathyapi.feature.account.payload.RegisterRequest;

public interface IAccountService {
    AuthResponse createAccount(RegisterRequest registerRequest);

    List<Account> getAllAccounts();

    Account getAccountById(Long id);

    Account updateAccount(Long id, Account account);

    void deleteAccount(Long id);

    AuthResponse authenticate(LoginRequest loginRequest);

    void updatePassword(Long id, String password);
}
