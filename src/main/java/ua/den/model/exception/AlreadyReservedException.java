package ua.den.model.exception;

public class AlreadyReservedException extends Exception {
    public AlreadyReservedException() {
    }

    public AlreadyReservedException(String message) {
        super(message);
    }

    public AlreadyReservedException(Long message) {
        super(Long.toString(message));
    }
}
