package com.api.controller;

import com.api.model.User;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Editor
 */
@RestController
@RequestMapping("/name")
public class UserController {
    @GetMapping
    public String getName(String name){
        return "getName your name is " + name;
    }

    @PostMapping
    public String postNameByParams(@RequestParam String name){
        return "postNameByParams your name " + name;
    }

    @PostMapping("/user")
    public ImmutablePair<Integer, String> postNameByBody(@RequestBody User user, HttpServletRequest request){
        String name = user.getName();
        String result = "Your name is " + name;
        return ImmutablePair.of(200, result);
    }
}
