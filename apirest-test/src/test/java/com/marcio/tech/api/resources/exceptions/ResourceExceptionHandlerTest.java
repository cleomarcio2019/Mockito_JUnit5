package com.marcio.tech.api.resources.exceptions;

import com.marcio.tech.api.services.exceptions.DataIntegrityViolationException;
import com.marcio.tech.api.services.exceptions.NoObjectFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void whenObjectNoFoundExceptionThenReturnResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler.objectNoFound(
                new NoObjectFoundException("Objeto não encontrado"),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("Objeto não encontrado", response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
        assertNotEquals("user/2", response.getBody().getPath());

    }

    @Test
    void dataIntegrityViolationException() {
        ResponseEntity<StandardError> response = exceptionHandler.dataIntegrityViolation(
                new DataIntegrityViolationException("Email já existe no Sistema!"),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("Email já existe no Sistema!", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }
}