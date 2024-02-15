package com.kuroko.heathyapi.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kuroko.heathyapi.feature.account.model.Account;
import com.kuroko.heathyapi.feature.account.model.Role;
import com.kuroko.heathyapi.feature.account.payload.RegisterRequest;
import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.feature.user.payload.UserReq;
import com.kuroko.heathyapi.feature.weight.Weight;

import lombok.Data;

@Component
@Data
public class ModelMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account mapToAccount(RegisterRequest registerRequest) {
        Account account = new Account();
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setEmail(registerRequest.getEmail());
        account.setRole(Role.USER);
        return account;
    }

    public User mapToUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setName(registerRequest.getName());
        user.setGoal(registerRequest.getGoal());
        user.setAge(registerRequest.getAge());
        user.setGender(registerRequest.getGender());
        user.setHeight(registerRequest.getHeight());
        user.setWeight(registerRequest.getWeight());
        user.setCoefficientOfActivity(registerRequest.getCoefficientOfActivity());
        return user;
    }

    public Weight maptoWeight(RegisterRequest registerRequest) {
        Weight weight = new Weight();
        weight.setWeight(registerRequest.getWeight());
        return weight;
    }

    public User mapToUser(UserReq userReq) {
        User user = new User();
        user.setName(userReq.getName());
        user.setGender(userReq.getGender());
        user.setHeight(Double.parseDouble(userReq.getHeight()));
        user.setWeight(Double.parseDouble(userReq.getWeight()));
        user.setCoefficientOfActivity(Double.parseDouble(userReq.getCoefficientOfActivity()));
        return user;
    }

}
