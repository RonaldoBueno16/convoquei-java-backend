package br.com.convoquei.backend.organizationInvite.exceptions;

import br.com.convoquei.backend._shared.exceptions.DomainNotFoundException;

public class OrganizationInviteNotFoundException extends DomainNotFoundException {
    public OrganizationInviteNotFoundException() {
        super("O convite especificado não foi encontrado.");
    }
}
