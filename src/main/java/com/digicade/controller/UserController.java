package com.digicade.controller;

import com.digicade.domain.User;
import com.digicade.service.UserService;
import com.digicade.service.dto.LeaderBoard;
import com.digicade.service.dto.UserProfileDTO;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.debug("REST request to get User by id: {}", id);
        User user = userService.getUserById(id);

        log.debug("REST request to get User by id: {}", user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user-profile/{id}")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable Long id) throws Exception {
        UserProfileDTO userProfile = userService.getUserProfile(id);

        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }
}
