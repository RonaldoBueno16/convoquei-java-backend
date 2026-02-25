package br.com.convoquei.backend.user.repository;

import br.com.convoquei.backend._template.repository.BaseRepository;
import br.com.convoquei.backend.user.model.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends BaseRepository<User, UUID> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}