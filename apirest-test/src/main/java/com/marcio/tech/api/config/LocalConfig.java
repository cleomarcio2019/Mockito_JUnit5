package com.marcio.tech.api.config;

import com.marcio.tech.api.domain.User;
import com.marcio.tech.api.domain.dto.UserDTO;
import com.marcio.tech.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public void startDB(){
        User u1 = new User(null, "Cleomarcio", "cleo_marcio2@yahoo.com.br", "138678");
        User u2 = new User(null, "Ana Maria", "anapodologia@gmail.com", "031925");
        User u3 = new User(null, "Isabelli Moreira", "isabelli123@gmail.com", "030402");
        User u4 = new User(null, "Nicholas Moreira", "nicholas_moreira2018@yahoo.com", "190899");
        userRepository.saveAll(List.of(u1, u2, u3, u4));
    }
}
