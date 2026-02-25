package br.com.convoquei.backend._shared.security.aspect;

import br.com.convoquei.backend._shared.security.annotation.RequiredOrganizationPermission;
import br.com.convoquei.backend.organizationRole.services.OrganizationPermissionService;
import br.com.convoquei.backend.user.provider.CurrentUserProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class OrganizationPermissionAspect {

    private final OrganizationPermissionService organizationPermissionService;
    private final HttpServletRequest request;
    private final CurrentUserProvider currentUserProvider;

    public OrganizationPermissionAspect(OrganizationPermissionService organizationPermissionService,
                                        HttpServletRequest request,
                                        CurrentUserProvider currentUserProvider) {
        this.organizationPermissionService = organizationPermissionService;
        this.request = request;
        this.currentUserProvider = currentUserProvider;
    }

    @Before("@annotation(requiredPermission)")
    public void checkPermission(JoinPoint joinPoint, RequiredOrganizationPermission requiredPermission) {
        UUID organizationId = resolveOrganizationId();
        UUID userId = currentUserProvider.userId().orElseThrow(() -> new AccessDeniedException("Usuário não autenticado."));

        organizationPermissionService.assertHasPermission(organizationId, userId, requiredPermission.value());
    }

    private UUID resolveOrganizationId() {
        String[] segments = request.getRequestURI().split("/");
        for (int i = 0; i < segments.length - 1; i++) {
            if ("organizations".equals(segments[i])) {
                return UUID.fromString(segments[i + 1]);
            }
        }
        throw new IllegalStateException("organizationId não encontrado na URL.");
    }
}
