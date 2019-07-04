package com.yinchuan.yunshu.controller;

import com.yinchuan.yunshu.service.UserService;
import com.yinchuan.yunshu.config.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for user functions
 * @author Siyuan Zeng
 *
 */
@RestController

public class UserController {
    /**
     * Service methods for user functions
     */
    @Autowired
    private UserService userService;

    /**
     * Find All Users Endpoint
     * @return List of Users from UserService method findAll
     */
    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     * Login Endpoint
     * @param username The inputed string corresponding to the username
     * @param password The inputed string corresponding to the password
     * @return ResponseEntity containing the error message if the credentials are incorrect or the User object if the credentials are correct
     */
    @RequestMapping(method = RequestMethod.POST, path = "/users/login")
    public ResponseEntity<?> authenticate(@RequestParam String username, String password) {
        return userService.authenticate(username, password);
    }

    /**
     * Find User By Id Endpoint
     * @param userId The id of the expected user
     * @return The User from the UserService method findById
     */
    @RequestMapping(method = RequestMethod.GET, path = "/users/{userId}")
    public User findById(@PathVariable int userId) {
        return userService.findById(userId);
    }

    /**
     * Create a New User Endpoint
     * @param user A user object containing at least the username and password, and possible the first and/or last name of the new user
     * @return Message from the UserService method createUser
     */
    @RequestMapping(method = RequestMethod.POST, path = "/users/createUser")
    public String createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    /**
     * Update user position
     */
    @RequestMapping(method = RequestMethod.POST, path = "/users/updatePosition")
    public String updatePosition(@RequestParam int userId, double latitude, double longtitude){
        return userService.updatePosition(userId, latitude, longtitude);
    }

    /**
     * Update user information
     */
    @RequestMapping(method = RequestMethod.POST, path = "/users/editUser")
    public String updatePosition(@RequestParam int targetId, int userType, String password){
        return userService.editUser(targetId, userType, password);
    }





}
