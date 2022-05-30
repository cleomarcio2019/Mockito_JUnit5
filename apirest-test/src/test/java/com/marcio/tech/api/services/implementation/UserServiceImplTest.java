package com.marcio.tech.api.services.implementation;

import com.marcio.tech.api.domain.User;
import com.marcio.tech.api.domain.dto.UserDTO;
import com.marcio.tech.api.repositories.UserRepository;
import com.marcio.tech.api.services.exceptions.DataIntegrityViolationException;
import com.marcio.tech.api.services.exceptions.NoObjectFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String EMAIL = "cleo_marcio2@yahoo.com.br";
    public static final String PASSWORD = "138678";
    public static final String NAME = "Cleomarcio Celestino";

    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper mapper;
    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnUserInstance() {
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);
        User response = userServiceImpl.findById(ID);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(User.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        when(userRepository.findById(anyInt()))
                .thenThrow(new NoObjectFoundException("Objeto não encontrado"));
        try{
            userServiceImpl.findById(ID);
        }catch(Exception ex){
            assertEquals(NoObjectFoundException.class, ex.getClass());
            assertEquals("Objeto não encontrado", ex.getMessage());
        }
    }

    @Test
    void whenFindAllAndReturnAnListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<User> response = userServiceImpl.findAll();
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getId());
        assertEquals(NAME, response.get(0).getName());
        assertEquals(EMAIL, response.get(0).getEmail());
        assertEquals(PASSWORD, response.get(0).getPassword());
    }

    @Test
    void whenCreateThenReturnSucess() {
        when(userRepository.save(any())).thenReturn(user);
        User response = userServiceImpl.createUser(userDTO);
        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnNotSuccessException() {
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);
        try{
            userServiceImpl.createUser(userDTO);
            optionalUser.get().setId(2);
        } catch(Exception ex){
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("Email já existe no Sistema!", ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSucess() {
        when(userRepository.save(any())).thenReturn(user);
        User response = userServiceImpl.updateUser(userDTO);
        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnNotSuccessException() {
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);
        try{
            userServiceImpl.createUser(userDTO);
            optionalUser.get().setId(2);
        } catch(Exception ex){
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("Email já existe no Sistema!", ex.getMessage());
        }
    }

    @Test
    void deleteWithSuccess() {
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);
        doNothing().when(userRepository).deleteById(anyInt());
        userServiceImpl.excludeById(ID);
        verify(userRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteWithNoSuccessButFailureException(){
        when(userRepository.findById(anyInt())).thenThrow(new NoObjectFoundException("Objeto não encontrado!"));
        try{
            userServiceImpl.excludeById(ID);
        } catch(Exception ex){
            assertEquals(NoObjectFoundException.class, ex.getClass());
            assertEquals("Objeto não encontrado!", ex.getMessage());
        }
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}