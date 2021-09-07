package com.api.sendaparcel.api.service;

import com.api.sendaparcel.api.dto.UserDTO;
import com.api.sendaparcel.api.util.enums.UserStatus;

import java.util.List;

public interface UserService {
    void registerUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    UserDTO getUser(String username);
    UserDTO getUser(long userId);
    List<UserDTO> getAllUsers(int pageNo, int count);
    void setUserStatus(UserStatus userStatus, long userId);
    UserStatus getUserStatus(long userId);
}
