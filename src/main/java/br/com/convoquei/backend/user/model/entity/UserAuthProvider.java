package br.com.convoquei.backend.user.model.entity;


import br.com.convoquei.backend._shared.seedwork.BaseEntity;
import br.com.convoquei.backend.user.model.enums.AuthProvider;
import jakarta.persistence.*;

@Entity
@Table(
        name = "user_auth_provider",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_provider_provider_user_id",
                        columnNames = {"provider", "provider_user_id"}
                ),
                @UniqueConstraint(
                        name = "uk_user_provider",
                        columnNames = {"user_id", "provider"}
                )
        }
)
public class UserAuthProvider extends BaseEntity {

    protected UserAuthProvider() {
    }

    public UserAuthProvider(User user, AuthProvider provider, String providerUserId, String email) {
        this.user = user;
        this.provider = provider;
        this.providerUserId = providerUserId;
        this.email = email;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_auth_providers_user")
    )
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", length = 50, nullable = false)
    private AuthProvider provider;

    @Column(name = "provider_user_id", length = 200, nullable = false)
    private String providerUserId;

    @Column(length = 150)
    private String email;

    public User getUser() {
        return user;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public String getEmail() {
        return email;
    }
}