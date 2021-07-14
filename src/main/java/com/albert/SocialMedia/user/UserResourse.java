package com.albert.SocialMedia.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserResourse {

    @Autowired
    UserDAOService userDAOService;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {

        return userDAOService.findAll();
    }

    /*@GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        User user = userDAOService.findOne(id);
        if(user == null){
            throw new UserNotFoundException("Id- " + id);
        }
        return user;
    }*/

    //HATEOAS
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        User user = userDAOService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("Id- " + id);
        }
        EntityModel<User> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder linkToUsers = linkTo(methodOn(getClass()).retrieveAllUsers());

        entityModel.add(linkToUsers.withRel("all-users"));
        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User users = userDAOService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteById(@PathVariable int id) {
        User user = userDAOService.deleteById(id);
        if (user == null) {
            throw new UserNotFoundException("id: - " + id);
        }
    }
}
