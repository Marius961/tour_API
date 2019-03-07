package ua.tour.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ReservationsExistException extends Exception {

    public ReservationsExistException(String msg) {
        super(msg);
    }
}
