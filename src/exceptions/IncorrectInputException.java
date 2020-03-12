package exceptions;

import java.io.IOException;

public class IncorrectInputException extends RuntimeException {
    public IncorrectInputException(String message) {
        super(message);
    }
}
