package com.digicade.controller;

import com.digicade.domain.User;
import com.digicade.repository.UserRepository;
import com.digicade.web.rest.UserResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        log.debug("REST request to get User by id: {}", id);
        Optional<User> optional = userRepository.findById(id);

        if (!optional.isPresent()) {
            return null;
        }

        User user = optional.get();

        log.debug("REST request to get User by id: {}", user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
