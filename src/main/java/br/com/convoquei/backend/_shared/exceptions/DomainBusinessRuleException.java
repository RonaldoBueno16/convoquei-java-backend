package br.com.convoquei.backend._shared.exceptions;

public class DomainBusinessRuleException extends RuntimeException {
    public DomainBusinessRuleException(String message) {
        super(message);
    }
}
