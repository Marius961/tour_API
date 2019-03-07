package ua.tour.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SeatsCountOverflowException extends Exception {

    public SeatsCountOverflowException(String msg) {
        super(msg);
    }
}
