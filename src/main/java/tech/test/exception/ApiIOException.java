package tech.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ApiIOException extends RuntimeException {
    public ApiIOException(String message) {
        super(message);
    }
}
