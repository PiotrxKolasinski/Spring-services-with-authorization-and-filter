package com.kolasinski.piotr.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService {
    private final static Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findOne(long id) {
        return userRepository.findById(id);
    }
}
