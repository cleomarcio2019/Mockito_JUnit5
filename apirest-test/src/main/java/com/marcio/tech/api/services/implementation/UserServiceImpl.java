package com.marcio.tech.api.services.implementation;

import com.marcio.tech.api.domain.User;
import com.marcio.tech.api.domain.dto.UserDTO;
import com.marcio.tech.api.repositories.UserRepository;
import com.marcio.tech.api.services.UserService;
import com.marcio.tech.api.services.exceptions.DataIntegrityViolationException;
import com.marcio.tech.api.services.exceptions.NoObjectFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(()-> new NoObjectFoundException("Objeto não encontrado"));
    }
    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }
    @Override
    public User createUser(UserDTO obj){
        findByEmail(obj);
        return userRepository.save(mapper.map(obj, User.class));
    }
    @Override
    public User updateUser(UserDTO obj){
        findByEmail(obj);
        return userRepository.save(mapper.map(obj, User.class));
    }

    @Override
    public void excludeById(Integer id){
        findById(id);
        userRepository.deleteById(id);
    }

    private void findByEmail(UserDTO obj){
        Optional<User> user = userRepository.findByEmail(obj.getEmail());
        if(user.isPresent() && !user.get().getId().equals(obj.getId())){
            throw new DataIntegrityViolationException("Email já existe no Sistema!");
        }
    }
}
