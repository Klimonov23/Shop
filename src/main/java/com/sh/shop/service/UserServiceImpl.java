package com.sh.shop.service;

import com.sh.shop.domain.Role;
import com.sh.shop.domain.User;
import com.sh.shop.dto.UserDTO;
import com.sh.shop.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder password_encoder;

    public UserServiceImpl( PasswordEncoder a, UserRepository b){
        this.password_encoder=a;
        this.userRepository=b;
    }

    @Override
    public boolean save(UserDTO userDTO) {
        if (!Objects.equals(userDTO.getPassword(),userDTO.getMatching_password())){
            throw new RuntimeException("passwords arent equal");
        }
        User user=User.builder()
                .name(userDTO.getUsername())
                .password(password_encoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .role(Role.CLIENT)
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findFirstByName(username);
        if (user==null) {
            throw new UsernameNotFoundException("user is not found");
        }
        List<GrantedAuthority> roles=new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                roles
        );
    }
    @Override
    public User findByName(String name){
        return userRepository.findFirstByName(name);
    }
    @Override
    public void updateProfile(UserDTO dto){
        User savedUser=userRepository.findFirstByName(dto.getUsername());
        if (savedUser==null) throw new RuntimeException("not found"+dto.getUsername());
        boolean isChanged=false;
        if (dto.getPassword()!=null && !dto.getPassword().isEmpty()) {
            isChanged=true;
            savedUser.setPassword(password_encoder.encode(dto.getPassword()));
        }
        if ( !Objects.equals(dto.getEmail(),savedUser.getEmail())){
            savedUser.setEmail(dto.getEmail());
            isChanged=true;
        }
        if (isChanged) userRepository.save(savedUser);
    }
}
