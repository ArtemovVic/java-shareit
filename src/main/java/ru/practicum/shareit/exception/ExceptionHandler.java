package ru.practicum.shareit.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), 404);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @Getter
    @Setter
    class ErrorResponse {
        private String message;
        private int status;
        private String error;

        public ErrorResponse(String message, int status) {
            this.message = message;
            this.status = status;
            this.error = message;

        }
    }
}
