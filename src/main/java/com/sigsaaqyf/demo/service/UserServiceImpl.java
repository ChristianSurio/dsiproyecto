package com.sigsaaqyf.demo.service;

import java.util.Optional;

import com.sigsaaqyf.demo.entity.User;
import com.sigsaaqyf.demo.repository.UserRepository;
//import com.sigsaaqyf.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository repository;

    @Override
    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }
    
    private boolean checkCarnetAvailble(User user) throws Exception {
        Optional <User> carnetFound = repository.findByCarnet(user.getCarnet());
        if (carnetFound.isPresent()) {
            throw new Exception("Carnet ya registrado");
        } 
        return true;
    }

    private boolean checkPasswordMatch(User user) throws Exception {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new Exception("contraseñas no coinciden");
        }
        return true;
    }

    @Override
    public User createUser(User user) throws Exception {
        if (checkCarnetAvailble(user) && checkPasswordMatch(user)) {
            user = repository.save(user);
        }
        return user;
    }
}