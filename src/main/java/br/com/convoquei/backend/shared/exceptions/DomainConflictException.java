package br.com.convoquei.backend.shared.exceptions;

public class DomainConflictException extends RuntimeException {
    public DomainConflictException(String message) {
        super(message);
    }
}
