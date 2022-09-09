package bugoverdose.mys3.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionListener {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        log.error("Internal Server Error", e);
        return new ResponseEntity<>(new ErrorResponse("예상치 못한 에러가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(InvalidRequestException e) {
        return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(UnauthenticatedException e) {
        return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(NotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.NOT_FOUND);
    }

    @Getter
    private static class ErrorResponse {

        Boolean ok = false;
        String error;

        ErrorResponse(String message) {
            this.error = message;
        }

        ErrorResponse(RuntimeException exception) {
            this(exception.getMessage());
        }
    }
}
