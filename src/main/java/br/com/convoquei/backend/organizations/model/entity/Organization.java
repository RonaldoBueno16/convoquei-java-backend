package br.com.convoquei.backend.organizations.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Organization {
    @Id
    private UUID id;

    @Column(length = 70, nullable = false)
    private String name;

    @Column(length = 150, nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String photoUrl;

    @Column(length = 30, nullable = false)
    private String status;

    @Column(nullable = false, columnDefinition = "TIMESTAMPTZ DEFAULT now()")
    private OffsetDateTime createdAt;
}