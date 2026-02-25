package br.com.convoquei.backend._shared.exceptions;

public class DomainConflictException extends DomainException {
    public DomainConflictException(String message) {
        super(message);
    }
}
