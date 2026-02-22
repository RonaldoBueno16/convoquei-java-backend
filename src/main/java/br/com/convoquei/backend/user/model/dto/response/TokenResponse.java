package br.com.convoquei.backend.user.model.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
