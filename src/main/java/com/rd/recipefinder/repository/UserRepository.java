package com.rd.recipefinder.repository;

import com.rd.recipefinder.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    UserEntity findByUsernameAndEmail(String username, String email);
    Optional<UserEntity> findByPwordVerificationToken(String pwordVerificationToken);
    Optional<UserEntity> findByEmailVerificationToken(String emailVerificationToken);
}
