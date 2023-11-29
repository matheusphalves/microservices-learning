package com.ms.user.services;

import com.ms.user.exceptions.UserException;
import com.ms.user.models.UserModel;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = UserException.class)
public class UserService {

    private final UserRepository userRepository;
    private final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    public UserModel saveUser(UserModel userModel) throws UserException {

        if(userRepository.existsByEmail(userModel.getEmail()))
            throw new UserException("User with email already exists.");

        userModel = userRepository.save(userModel);
        userProducer.publishEmailMessage(userModel);

        return userModel;
    }

}
