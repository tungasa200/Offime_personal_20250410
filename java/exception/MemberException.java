package Offime.Offime.exception;

import org.springframework.http.HttpStatus;

public class MemberException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public MemberException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
