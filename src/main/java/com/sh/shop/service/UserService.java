package com.sh.shop.service;

import com.sh.shop.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean save(UserDTO userDTO) ;
    List<UserDTO> getAll();
}
