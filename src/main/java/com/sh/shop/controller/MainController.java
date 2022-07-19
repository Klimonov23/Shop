package com.sh.shop.controller;

import com.sh.shop.service.SessionObjectHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class MainController {
    private final SessionObjectHolder sessionObjectHolder;

    public MainController(SessionObjectHolder sessionObjectHolder) {
        this.sessionObjectHolder = sessionObjectHolder;
    }

    @RequestMapping({"","/"})
    public String index(Model model, HttpSession session){
        model.addAttribute("AmountClicks",sessionObjectHolder.getAmountClicks());
        if (session.getAttribute("myID")==null){
            String uuid= UUID.randomUUID().toString();
            session.setAttribute("myID",uuid);
            System.out.println("generated id"+uuid);
        }
        model.addAttribute("uuid",session.getAttribute("myID"));
        return "index";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError",true);
        return "login";
    }
}
