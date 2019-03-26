package model.exception;

public class AlreadyReservedException extends Exception {
    public AlreadyReservedException() {
    }

    public AlreadyReservedException(String message) {
        super(message);
    }
}
