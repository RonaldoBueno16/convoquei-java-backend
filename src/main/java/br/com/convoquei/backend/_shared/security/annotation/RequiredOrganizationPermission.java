package br.com.convoquei.backend._shared.security.annotation;

import br.com.convoquei.backend.organizationRole.model.enums.OrganizationPermission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredOrganizationPermission {
    OrganizationPermission value();
}