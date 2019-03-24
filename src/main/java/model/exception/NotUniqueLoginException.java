package model.exception;

public class NotUniqueLoginException extends Exception {
    public NotUniqueLoginException(String message) {
        super("Login " + message + " already exists in the system");
    }
}
