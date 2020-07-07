package com.sigsaaqyf.demo.controller;

import com.sigsaaqyf.demo.service.UserService;

import javax.validation.Valid;

import com.sigsaaqyf.demo.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index() {
        return "user-form/login";
    }

    @GetMapping("/userList")
    public String userList(Model model) {
        model.addAttribute("userList", userService.getAllUsers());
        return "user-form/user-list";
    }

    @GetMapping("/userRegister")
    public String userForm(Model model) {
        model.addAttribute("userForm", new User());
        return "user-form/user-register";
    }

    @PostMapping("/userRegister")
    public String userForm(@Valid @ModelAttribute("userForm") User user, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("userForm", user);
            return "user-form/user-register";
        } else {
            try {
                userService.createUser(user);
            } catch (Exception e) {
                model.addAttribute("errorMessage",e.getMessage());
                model.addAttribute("userForm", user);
                return "user-form/user-register";
            }
        }
        return "user-form/login";
    }

    @GetMapping("/userEdit/{id}")
    public String editUser(Model model, @PathVariable(name = "id")Long id) throws Exception {
        User userToEdit = userService.getUserById(id);
        model.addAttribute("userForm",userToEdit);
        model.addAttribute("userEditMode", true);

        return "user-form/user-register";
    }

    @PostMapping("/userEdit")
    public String editUser(@Valid @ModelAttribute("userForm")User userFrom, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("userForm", userFrom);
            model.addAttribute("userEditMode", true);
            return "user-form/user-register";
        }
        try {
            userService.updateUser(userFrom);
            model.addAttribute("userList", userService.getAllUsers());
            return "user-form/user-list";
        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            model.addAttribute("userForm", userFrom);
            model.addAttribute("userEditMode", true);
            return "user-form/user-register";
        }

        
    }
}




















