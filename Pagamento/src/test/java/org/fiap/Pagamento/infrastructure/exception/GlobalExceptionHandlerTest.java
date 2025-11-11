package org.fiap.Pagamento.infrastructure.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.fiap.Pagamento.core.exception.RecursoNaoEncontradoExcecao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void deveTratarRecursoNaoEncontradoExcecao() {
        RecursoNaoEncontradoExcecao ex = new RecursoNaoEncontradoExcecao("Recurso não encontrado");

        ResponseEntity<Map<String, Object>> response = handler.handleRecursoNaoEncontradoException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Recurso não encontrado", response.getBody().get("message"));
        assertEquals(404, response.getBody().get("status"));
    }

    @Test
    void deveTratarExcecaoGenerica() {
        Exception ex = new Exception("Erro genérico");

        ResponseEntity<Map<String, Object>> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro genérico", response.getBody().get("message"));
        assertEquals(500, response.getBody().get("status"));
    }

    @Test
    void deveTratarIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Argumento inválido");

        ResponseEntity<Map<String, Object>> response = handler.handleIllegalArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Argumento inválido", response.getBody().get("message"));
        assertEquals(400, response.getBody().get("status"));
    }

    @Test
    void deveTratarDataIntegrityViolationException() {
        Throwable cause = new Throwable("Chave duplicada");
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Erro de integridade", cause);

        ResponseEntity<Map<String, Object>> response = handler.handleDataIntegrity(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().get("message").toString().contains("Violação de integridade"));
    }

    @Test
    void deveTratarConstraintViolationException() {
        @SuppressWarnings("unchecked")
        ConstraintViolation<Object> violation = mock(ConstraintViolation.class);
        Path mockPath = mock(Path.class);
        when(mockPath.toString()).thenReturn("campoTeste");
        when(violation.getPropertyPath()).thenReturn(mockPath);
        when(violation.getMessage()).thenReturn("não pode ser nulo");

        ConstraintViolationException ex = new ConstraintViolationException(Set.of(violation));

        ResponseEntity<Map<String, Object>> response = handler.handleConstraint(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().get("message").toString().contains("campoTeste"));
        assertTrue(response.getBody().get("message").toString().contains("não pode ser nulo"));
    }


    @Test
    void deveTratarMethodArgumentNotValidException() {
        var fieldError = new FieldError("objeto", "campo", "não pode ser vazio");
        var bindingResult = mock(org.springframework.validation.BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, Object>> response = handler.handleMethodArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().get("message").toString().contains("campo: não pode ser vazio"));
    }
}
