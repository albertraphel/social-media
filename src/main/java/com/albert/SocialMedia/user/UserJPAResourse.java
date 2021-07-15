package com.albert.SocialMedia.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJPAResourse {

    @Autowired
    UserJPARepository userJPARepository;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers() {

        return userJPARepository.findAll();
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
    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        Optional<User> user = userJPARepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("Id- " + id);
        }
        EntityModel<User> entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder linkToUsers = linkTo(methodOn(getClass()).retrieveAllUsers());

        entityModel.add(linkToUsers.withRel("all-users"));
        return entityModel;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User users = userJPARepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteById(@PathVariable int id) {
        userJPARepository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveAllUsers(@PathVariable int id) {
        Optional<User> user = userJPARepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("Id- " + id);
        }
        return user.get().getPost();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPosts(@PathVariable int id, @RequestBody Post post) {
        Optional<User> optional = userJPARepository.findById(id);
        if (!optional.isPresent()) {
            throw new UserNotFoundException("Id- " + id);
        }
        User user = optional.get();
        post.setUser(user);
        postRepository.save(post);
        User users = userJPARepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
