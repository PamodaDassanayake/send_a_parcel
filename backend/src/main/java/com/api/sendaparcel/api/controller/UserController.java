package com.api.sendaparcel.api.controller;

import com.api.sendaparcel.api.dto.UserDTO;
import com.api.sendaparcel.api.exceptions.ConflictException;
import com.api.sendaparcel.api.exceptions.NotAllowedResourceException;
import com.api.sendaparcel.api.exceptions.UserNotFoundException;
import com.api.sendaparcel.api.security.model.SpringSecurityUser;
import com.api.sendaparcel.api.service.UserService;
import com.api.sendaparcel.api.util.enums.UserStatus;
import com.api.sendaparcel.api.util.enums.UserType;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(name = "user_controller", value = "/api/v1/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringSecurityUser requestedUser = (SpringSecurityUser) authentication.getPrincipal();

        UserDTO user = service.getUser(id);
        boolean isValid = false;
        if(requestedUser.getRole().equals(UserType.ADMIN.name())) {
            isValid = true;
        } else {
            UserDTO requestedUserDTO = service.getUser(requestedUser.getUsername());
            if(user != null && user.getUserId() == requestedUserDTO.getUserId()) {
                isValid = true;
            }
        }

        if(user != null) {
            if(isValid)
                return new ResponseEntity(user,HttpStatus.OK);
            else
                throw new NotAllowedResourceException("you are not able to access this resource");
        } else {
            throw new UserNotFoundException("User does not exists");
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@NotNull @RequestBody UserDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringSecurityUser requestedUser = (SpringSecurityUser) authentication.getPrincipal();
        UserDTO user = service.getUser(userDTO.getUserId());
        UserDTO requestedUserDTO = service.getUser(requestedUser.getUsername());
        if(user == null) {
            throw new UserNotFoundException("User does not exists");
        } else {
            if(user.getUserId() == requestedUserDTO.getUserId()) {
                service.updateUser(userDTO);
                return new ResponseEntity("Your account has been updated successfully!",
                        HttpStatus.OK);
            } else {
                throw new NotAllowedResourceException("you are not able to access this resource");
            }
        }
    }

    @GetMapping
    // @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?> find(@RequestParam("page_no") int pageNo, @RequestParam("count") int count) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringSecurityUser requestedUser = (SpringSecurityUser) authentication.getPrincipal();
        if(!requestedUser.getRole().equals(UserType.ADMIN.name())) {
            throw new NotAllowedResourceException("you are not allowed access this resource!");
        }
        List<UserDTO> userDTOList = service.getAllUsers(pageNo,count);
        return new ResponseEntity(userDTOList,HttpStatus.OK);
    }

    @PutMapping("/status")
    public ResponseEntity<?> setUserStatus(@RequestParam("status") String status) {

        UserStatus userStatus;
        try {
            userStatus = UserStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new ConflictException("Unknown user status");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringSecurityUser requestedUser = (SpringSecurityUser) authentication.getPrincipal();

        UserDTO user = service.getUser(requestedUser.getUsername());
        boolean isValid = false;
        UserDTO requestedUserDTO = service.getUser(requestedUser.getUsername());
        if(user != null && user.getUserId() == requestedUserDTO.getUserId()) {
            isValid = true;
        }

        if(user != null) {
            if(isValid) {
                service.setUserStatus(userStatus,user.getUserId());
                return new ResponseEntity(String.format("User : %d updated status : %s",user.getUserId(),userStatus)
                        ,HttpStatus.OK);
            }
            else
                throw new NotAllowedResourceException("you are not able to access this resource");
        } else {
            throw new UserNotFoundException("User does not exists");
        }
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<?> getUserStatus(@PathVariable("id") long id) {
        UserStatus userStatus = service.getUserStatus(id);
        return new ResponseEntity(userStatus,HttpStatus.OK);
    }
}
