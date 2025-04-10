package Offime.Offime.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UnauthorizedAccessException.class)
  public ResponseEntity<Map<String, String>> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
//    System.out.println("UnauthorizedAccessException 발생: " + ex.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", ex.getMessage()));
  }

  @ExceptionHandler(DisabledException.class)
  public ResponseEntity<Map<String, String>> handleDisabledException(DisabledException ex) {
//    System.out.println("DisabledException 발생: " + ex.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", ex.getMessage()));
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
//    System.out.println("ResourceNotFoundException 발생: " + ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "존재하지 않는 계정입니다."));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
  }
}