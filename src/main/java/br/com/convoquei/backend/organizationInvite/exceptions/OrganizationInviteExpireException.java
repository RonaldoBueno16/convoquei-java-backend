package br.com.convoquei.backend.organizationInvite.exceptions;

import br.com.convoquei.backend._shared.exceptions.DomainBusinessRuleException;

import java.time.OffsetDateTime;

public class OrganizationInviteExpireException extends DomainBusinessRuleException {
    public OrganizationInviteExpireException(OffsetDateTime expireAt) {
        super("O convite expirou em %s".formatted(expireAt));
    }
}
