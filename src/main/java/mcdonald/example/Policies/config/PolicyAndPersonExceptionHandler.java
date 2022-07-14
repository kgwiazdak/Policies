package mcdonald.example.Policies.config;

import mcdonald.example.Policies.service.exceptions.DataAlreadyInDatabase;
import mcdonald.example.Policies.service.exceptions.DataNotInDatabase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class PolicyAndPersonExceptionHandler {

    @ExceptionHandler(DataAlreadyInDatabase.class)
    public ResponseEntity<ErrorResponse> handleDataAlreadyInDataBase(DataAlreadyInDatabase ex) {
        ErrorResponse errors = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataNotInDatabase.class)
    public ResponseEntity<ErrorResponse> handleDataNotInDatabase(DataNotInDatabase ex) {
        ErrorResponse errors = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    private static final class ErrorResponse {
        private final String message;


        public String getMessage() {
            return this.message;
        }


        public ErrorResponse(String message) {
            this.message = message;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ErrorResponse that = (ErrorResponse) o;
            return Objects.equals(message, that.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(message);
        }
    }
}
