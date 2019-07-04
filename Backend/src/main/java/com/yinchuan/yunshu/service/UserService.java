package com.yinchuan.yunshu.service;

import com.yinchuan.yunshu.config.User;
import com.yinchuan.yunshu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Methods for user functions
 *
 * @author Siyuan Zeng
 *
 */
@Service
public class UserService {
    /**
     * Interface for connecting to user table
     */
    @Autowired
    private UserRepository users;

    /**
     * Get all users in the user table
     *
     * @return The List of Users from the UserRepository method findAll
     */
    public List<User> findAll() {
        return users.findAll();
    }

    /**
     * Login method
     *
     * @param username
     *            The inputed string corresponding to the username
     * @param password
     *            The inputed string corresponding to the password
     * @return Response Entity containing message if username and/or password are
     *         incorrect. If username is null returns the message "Username cannot
     *         be empty". If the password is null returns the message "Password
     *         cannot be empty". If the username is invalid returns the message
     *         "User does not exist". If the user is banned returns the message "You
     *         are banned". If the password is incorrect returns "Incorrect
     *         Password". Otherwise returns the User object.
     */
    public ResponseEntity<?> authenticate(String username, String password) {
        if (username == null) {
            return new ResponseEntity<>("Username cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (password == null) {
            return new ResponseEntity<>("Password cannot be empty", HttpStatus.BAD_REQUEST);
        }
        User user = users.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        }
        if (!user.getPassword().equals(password)) {
            return new ResponseEntity<>("Incorrect Password", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    /**
     * Returns the user with the provided id
     *
     * @param id
     *            The id of the expected user
     * @return The User from the UserRepository method findById
     */
    public User findById(int id) {
        return users.findById(id);
    }

    /**
     * Creates a new user and saves them to the database
     *
     * @param user
     *            A user object containing at least the username and password, and
     *            possible the first and/or last name of the new user
     * @return If the user object is null returns "User cannot be null". If the
     *         username of the user object is null returns "Username cannot be
     *         null". If the password of the user object is null returns "Password
     *         cannot be null". If the password does not meet the password
     *         requirements returns "Password must be 8 characters or longer
     *         Password must contain at least one uppercase Password must contain at
     *         least one lowercase level". If the username is the save as the
     *         username of another user returns "Username taken". Otherwise returns
     *         "User {username} created".
     */
    public String createUser(User user) {
        if (user == null) {
            return "用户不能为空";
        }
        if (user.getUsername().equals(null)) {
            return "用户名不能为空";
        }
        if (user.getPassword().equals(null)) {
            return "密码不能为空";
        }

        if (!isValidPassword(user.getPassword())) {
            return "密码至少为8位数以上 \n密码必须包含至少一个大写字母 \n密码必须包含至少一个小写字母";
        }

        if (users.findByUsername(user.getUsername()) != null) {
            return "用户名已被占用";
        }
        this.users.save(user);
        return "User " + user.getUsername() + " created";
    }

    public String updatePosition(int userId, double latitude, double longtitude){
        User targetUser = this.users.findById(userId);
        if(targetUser != null){
            targetUser.setLatitude(latitude);
            targetUser.setLongtitude(longtitude);
            this.users.save(targetUser);
            return "User "+targetUser.getUsername()+ " position has been updated";
        }
        else{
            return "User not found.";
        }
    }

    public String editUser(int targetId, int userType, String password){
        User targetUser = users.findById(targetId);
        targetUser.setUserType(userType);
        targetUser.setPassword(password);
        if (!isValidPassword(targetUser.getPassword())) {
            return "密码至少为8位数以上 \n密码必须包含至少一个大写字母 \n密码必须包含至少一个小写字母";
        }
        else {
            this.users.save(targetUser);
            return "更改成功";
        }

    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean containsUpperCase = false;
        boolean containsLowerCase = false;
        for (int x = 0; x < password.length(); x++) {
            char c = password.charAt(x);
            if (Character.isUpperCase(c)) {
                containsUpperCase = true;
            }
            if (Character.isLowerCase(c)) {
                containsLowerCase = true;
            }
        }
        if (!containsLowerCase || !containsUpperCase) {
            return false;
        }
        return true;
    }
}
