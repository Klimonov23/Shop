package com.sh.shop.controller;

import com.sh.shop.domain.User;
import com.sh.shop.dto.UserDTO;
import com.sh.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("user",new UserDTO());
        return "user";
    }
    @GetMapping
    public String userList(Model model){
        //if (true) throw new RuntimeException("test of ErrorHandler");
        model.addAttribute("users",userService.getAll());
        return "userList";
    }
    @PostMapping("/new")
    public String saveUser(UserDTO userDTO,Model model){
        if (userService.save(userDTO)){
            return "redirect:/users";
        }
        else {
            model.addAttribute("user",userDTO);
            return "user";
        }
    }
    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal){
        if (principal==null) throw new RuntimeException("you are not authorized");
        User user=userService.findByName(principal.getName());

        UserDTO dto=UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
        model.addAttribute("user",dto);
        return "profile";
    }
    @PostMapping("/profile")
    public String updateProfileUser(UserDTO dto,Model model,Principal principal){
        if (principal==null|| !Objects.equals(principal.getName(),dto.getUsername())) throw new RuntimeException("you are not authorized");
        if (dto.getPassword()!=null&&!dto.getPassword().isEmpty()
                &&!Objects.equals(dto.getPassword(),dto.getMatching_password())){
            model.addAttribute("user",dto);
            return "profile";
        }
        userService.updateProfile(dto);
        return "redirect:/users/profile";
    }
}
