package api.test.meetingplanner.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTimeException.class)
    public ResponseEntity<String> handleInvalidTimeException(InvalidTimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UnavailableSalleException.class)
    public ResponseEntity<String> handleUnavailableSalleException(UnavailableSalleException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }

    @ExceptionHandler(SuccesReservationException.class)
    public ResponseEntity<String> handleSuccesReservationException(Exception ex) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ex.getMessage());
    }


}


