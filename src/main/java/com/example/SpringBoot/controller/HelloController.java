package com.example.SpringBoot.controller;

import com.example.SpringBoot.dao.DAO;


import com.example.SpringBoot.model.User;
import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {
    private DAO dao;

    @Autowired
    public HelloController(DAO dao) {
        this.dao = dao;
    }

    @GetMapping(value = "/")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("Welcome to start page!");
        model.addAttribute("messages", messages);
        return "hello";
    }
    @ModelAttribute("newUser")
    public User getPerson(){
        return new User();
    }
    @GetMapping("/people")
	public String index(Model model){
    	model.addAttribute("people",dao.getAllUsers());
    	return "view/index";
	}

    @PostMapping("/people")
    public String creat(@ModelAttribute("newUser")@Valid User user,
                        BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("people",dao.getAllUsers());
            return "view/index";
        }
    	dao.saveUser(user);
    	return "redirect:/people";
    }

    @DeleteMapping("/people/{id}")
    public String deletePerson(@PathVariable("id") int id){
        dao.removeUserById(id);
        return "redirect:/people";
    }
    @GetMapping("/people/{id}/edit")
    public String edit (@ModelAttribute("id") int id,Model model){
        model.addAttribute("user",dao.getUserById(id));
        return "view/edit";
    }

    @PatchMapping("/people/{id}")
    public String updatePerson(@ModelAttribute("user")@Valid User updateuser, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "view/edit";
        }
        dao.updateUser(updateuser);
        return "redirect:/people";
    }
}