package br.com.convoquei.backend._shared.seedwork;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    protected UUID id;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMPTZ DEFAULT now()"
    )
    protected OffsetDateTime createdAt = OffsetDateTime.now();

    @LastModifiedDate
    @Column(name = "updated_at")
    protected OffsetDateTime updatedAt = OffsetDateTime.now();

    @PrePersist
    void beforeCreate() {
        if (id == null) {
            id = UUID.randomUUID();
        }

        onPrePersist();
    }

    protected void onPrePersist() {

    }
}
