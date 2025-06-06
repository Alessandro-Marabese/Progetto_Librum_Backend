package it.epicode.Progetto_Librum_Backend.exception;

public class UsernameException extends RuntimeException {
    public UsernameException(String message) {
        super(message);
    }
}