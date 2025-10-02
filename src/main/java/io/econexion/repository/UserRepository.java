package io.econexion.repository;

import java.util.Optional;
import java.util.UUID;

import io.econexion.model.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDto, UUID> {

    Optional<UserDto> findByEmail(String email);

}
