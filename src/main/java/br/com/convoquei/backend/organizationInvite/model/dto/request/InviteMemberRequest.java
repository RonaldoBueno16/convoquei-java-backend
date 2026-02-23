package br.com.convoquei.backend.organizationInvite.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InviteMemberRequest(
        @NotBlank @Email String email
) {
}
