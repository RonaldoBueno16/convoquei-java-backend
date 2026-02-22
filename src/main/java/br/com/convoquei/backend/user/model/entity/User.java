package br.com.convoquei.backend.user.model.entity;

import br.com.convoquei.backend.shared.model.entity.BaseEntity;
import br.com.convoquei.backend.user.model.enums.UserStatus;
import jakarta.persistence.*;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_user_email", columnList = "email")
        }
)
public class User extends BaseEntity {

    protected User() {
    }

    public User(String name, String email, String passwordHash) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isEmailVerified = false;
        this.status = UserStatus.ACTIVE;
    }

    @Column(length = 70, nullable = false)
    private String name;

    @Column(length = 200, nullable = false, unique = true)
    public String email;

    @Column(name = "password_hash", columnDefinition = "TEXT")
    private String passwordHash;

    @Column(name = "avatar_url", columnDefinition = "TEXT")
    private String avatarUrl;

    @Column(name = "is_email_verified", nullable = false)
    private boolean isEmailVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }
}