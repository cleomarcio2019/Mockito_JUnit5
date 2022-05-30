package com.marcio.tech.api.resources;

import com.marcio.tech.api.domain.User;
import com.marcio.tech.api.domain.dto.UserDTO;
import com.marcio.tech.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class UserResource {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(mapper.map(userService.findById(id), UserDTO.class));
    }
    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> findAll(){
        List<User> list = userService.findAll();
        List<UserDTO> listDTO = list.stream().map(x-> mapper.map(x, UserDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping("/insert")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO obj){
        User newObj = userService.createUser(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateClient(@PathVariable Integer id, @RequestBody UserDTO obj){
        obj.setId(id);
        User newObj = userService.updateUser(obj);
        return ResponseEntity.ok().body(mapper.map(newObj, UserDTO.class));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Integer id){
        userService.excludeById(id);
        return ResponseEntity.noContent().build();
    }
}
