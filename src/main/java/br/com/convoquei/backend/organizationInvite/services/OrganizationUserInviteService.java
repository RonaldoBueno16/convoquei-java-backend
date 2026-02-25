package br.com.convoquei.backend.organizationInvite.services;

import br.com.convoquei.backend._shared.helpers.TransactionHelper;
import br.com.convoquei.backend._shared.model.dto.request.PagedRequest;
import br.com.convoquei.backend._shared.model.dto.response.PagedResponse;
import br.com.convoquei.backend.organization.model.entity.Organization;
import br.com.convoquei.backend.organizationInvite.exceptions.OrganizationInviteExpireException;
import br.com.convoquei.backend.organizationInvite.exceptions.OrganizationInviteNotFoundException;
import br.com.convoquei.backend.organizationInvite.model.dto.response.OrganizationInviteDetailsResponse;
import br.com.convoquei.backend.organizationInvite.model.entity.OrganizationInvite;
import br.com.convoquei.backend.organizationInvite.repository.OrganizationInviteRepository;
import br.com.convoquei.backend.user.model.entity.User;
import br.com.convoquei.backend.user.provider.CurrentUserProvider;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class OrganizationUserInviteService {

    private static final Logger log = LoggerFactory.getLogger(OrganizationUserInviteService.class);
    private final CurrentUserProvider currentUserProvider;
    private final OrganizationInviteRepository organizationInviteRepository;
    private final TransactionHelper transactionHelper;

    public OrganizationUserInviteService(CurrentUserProvider currentUserProvider, OrganizationInviteRepository organizationInviteRepository, TransactionHelper transactionHelper) {
        this.currentUserProvider = currentUserProvider;
        this.organizationInviteRepository = organizationInviteRepository;
        this.transactionHelper = transactionHelper;
    }

    public PagedResponse<OrganizationInviteDetailsResponse> listUserInvites(PagedRequest request) {
        User user = currentUserProvider.requireUser();

        Page<OrganizationInviteDetailsResponse> pageResult = organizationInviteRepository.findAllByInvitedEmailIgnoreCaseAndExpiresAtAfter(user.getEmail(), OffsetDateTime.now(), request.toPageRequest());

        return PagedResponse.buildFromPage(pageResult);
    }

    @Transactional
    public void acceptInvite(UUID inviteId) {
        OrganizationInvite invite = fetchAndValidateInvite(inviteId);
        validateInviteExpiration(invite);

        Organization organization = invite.getOrganization();

        organization.addMember(currentUserProvider.requireUser());

        deleteInvite(inviteId);
    }

    @Transactional
    public void declineInvite(UUID inviteId) {
        OrganizationInvite invite = fetchAndValidateInvite(inviteId);
        validateInviteExpiration(invite);

        deleteInvite(inviteId);
    }

    private void validateInviteExpiration(OrganizationInvite invite) {
        if (invite.isExpired()) {
            OffsetDateTime expiresAt = invite.getExpiresAt();

            deleteInviteWithNewTransaction(invite.getId());

            throw new OrganizationInviteExpireException(expiresAt);
        }
    }

    private void deleteInviteWithNewTransaction(UUID inviteId) {
        transactionHelper.executeWithNewTransaction(() -> deleteInvite(inviteId));
    }

    private void deleteInvite(UUID inviteId) {
        organizationInviteRepository.deleteById(inviteId);
    }

    private OrganizationInvite fetchAndValidateInvite(UUID inviteId) {
        User user = currentUserProvider.requireUser();

        return organizationInviteRepository.findById(inviteId)
                .filter(invite -> invite.getInvitedEmail().equalsIgnoreCase(user.getEmail()))
                .orElseThrow(OrganizationInviteNotFoundException::new);
    }
}
