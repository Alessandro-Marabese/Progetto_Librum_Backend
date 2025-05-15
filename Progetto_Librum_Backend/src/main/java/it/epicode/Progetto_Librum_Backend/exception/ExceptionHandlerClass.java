package it.epicode.Progetto_Librum_Backend.exception;

import it.epicode.Progetto_Librum_Backend.auth.ExceptionMessage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerClass extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFound(EntityNotFoundException ex) {
        return new ResponseEntity<>("Entity not found | " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleUserNotFoundException(UsernameNotFoundException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setMessage(e.getMessage());
        exceptionMessage.setStatus("404");
        exceptionMessage.setError("User Not Found: " + e.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }
    // validazione da service
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            if (fieldName.contains(".")) {
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
            }
            errors.put(fieldName, violation.getMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>("Errore di validazione: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    // validazione da controller
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (var error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), "Errore controller: " + error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    protected ResponseEntity<ExceptionMessage> AccessDenied(AccessDeniedException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setMessage("Access denied");
        exceptionMessage.setStatus("403");
        exceptionMessage.setError(e.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = SecurityException.class)
    protected ResponseEntity<ExceptionMessage> handlerSecurityException(SecurityException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setMessage("Access denied: " + e.getMessage());
        exceptionMessage.setStatus("401");
        exceptionMessage.setError(e.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.UNAUTHORIZED);
    }
}