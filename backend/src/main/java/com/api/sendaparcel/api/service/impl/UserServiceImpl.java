package com.api.sendaparcel.api.service.impl;

import com.api.sendaparcel.api.dto.UserDTO;
import com.api.sendaparcel.api.entity.User;
import com.api.sendaparcel.api.exceptions.AlreadyExistsException;
import com.api.sendaparcel.api.exceptions.UserNotFoundException;
import com.api.sendaparcel.api.exceptions.RequestDataValidationException;
import com.api.sendaparcel.api.repository.UserRepository;
import com.api.sendaparcel.api.service.UserService;
import com.api.sendaparcel.api.util.Validation;
import com.api.sendaparcel.api.util.enums.UserStatus;
import com.api.sendaparcel.api.util.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private void validateUser(UserDTO userDTO, boolean isRegister) {
        List<String> errors = new ArrayList<>();
        // validate user information
        if(userDTO.getUsername().trim().length() < 6) {
            errors.add("Username must be contains at least 6 characters");
        }

        if(isRegister && !(userDTO.getUserType() instanceof UserType)) {
            errors.add("Unknown UserType");
        }

        if(isRegister && !Validation.isStrongPassword(userDTO.getPassword())) {
            errors.add("Weak Password");
        }

        if(userDTO.getAddress().trim().isEmpty()) {
            errors.add("Address required");
        }

        if(!errors.isEmpty()) {
            String info = Arrays.asList(errors).stream()
                    .map(Object::toString)
                    .collect(Collectors.joining("\n"));
            throw new RequestDataValidationException(info);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void registerUser(UserDTO userDTO) {
        Optional<User> userOptional = repository.findUserByUsername(userDTO.getUsername());
        if(userOptional.isPresent()) {
            throw new AlreadyExistsException();
        }
        User user = new User();
        validateUser(userDTO, true);
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setAddress(userDTO.getAddress());
        user.setUserType(userDTO.getUserType());
        user.setCreation(new Date());
        repository.save(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateUser(UserDTO userDTO) {
        Optional<User> existingUser = repository.findById(userDTO.getUserId());
        if(!existingUser.isPresent()) {
            throw new UserNotFoundException(userDTO.getUserId() + " user does not exits");
        }
        // validateUser(userDTO);
        Optional<User> userOptional = repository.findById(userDTO.getUserId());
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException(String.format("User not found"));
        }
        User user = userOptional.get();
        validateUser(userDTO,false);
        user.setUsername(userDTO.getUsername());
        user.setAddress(userDTO.getAddress());
        repository.save(user);
    }

    @Override
    public UserDTO getUser(String username) {
        Optional<User> userOptional = repository.findUserByUsername(username);
        if(userOptional.isPresent())  {
            UserDTO userDTO = new UserDTO();
            User user = userOptional.get();
            userDTO.setUserId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setAddress(user.getAddress());
            userDTO.setUserType(user.getUserType());
            return userDTO;
        }
        return null;
    }

    @Override
    public UserDTO getUser(long userId) {
        Optional<User> userOptional =  repository.findById(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setAddress(user.getAddress());
            userDTO.setUserType(user.getUserType());
            return userDTO;
        }
        return null;
    }

    @Override
    public List<UserDTO> getAllUsers(int pageNo, int count) {
        List<UserDTO> userDTOS = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNo,count);
        Page<User> users = repository.findAll(pageable);
        for(User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setAddress(user.getAddress());
            userDTO.setUserType(user.getUserType());
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    @Override
    public void setUserStatus(UserStatus userStatus, long userId) {
        Optional<User> userOptional = repository.findById(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUserStatus(userStatus);
            repository.save(user);
            return;
        }
        throw new UserNotFoundException("User not found");
    }

    @Override
    public UserStatus getUserStatus(long userId) {
        Optional<User> userOptional = repository.findById(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getUserStatus();
        }
        throw new UserNotFoundException("User not found");
    }
}
