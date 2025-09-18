package io.econexion.lab.users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.econexion.lab.users.dto.UserDto;

@Repository
public interface LabUserRepository extends JpaRepository<UserDto, UUID> {

    Optional<UserDto> findByEmail(String email);

}
