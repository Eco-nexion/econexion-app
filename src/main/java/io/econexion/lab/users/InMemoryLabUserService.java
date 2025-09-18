package io.econexion.lab.users;

import io.econexion.lab.users.dto.*;

import java.util.*;
import org.springframework.stereotype.Service;      // <-- import necesario
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile; // <-- import necesario

@Service
@Profile("lab")
public class InMemoryLabUserService {
    @Autowired
    private final LabUserRepository repository;

    // private final Map<UUID, UserDto> store = new ConcurrentHashMap<>();

    public InMemoryLabUserService(LabUserRepository repository) {
        this.repository = repository;

        // puedes inicializar datos aquí
        UserDto newUser = new UserDto(
            "Ada Lovelace",
            "ada@demo.test",
            "password123"
        );
        repository.save(newUser);
    }

    public List<UserDto> findAll() {

        return repository.findAll();
    }

    public Optional<UserDto> findById(UUID id) {
        return repository.findById(id);
    }

    public UserDto create(UserDto user) {
        UserDto newUser = repository.save(user);
        return newUser;
    }

    public Optional<UserDto> update(UUID id, UserDto newUser) {
        UserDto user = repository.findById(id).orElse(null);

        if (user == null) {
            return Optional.empty();
        }
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        repository.save(user);

        return Optional.of(user);
        
    }

    public boolean delete(UUID id) {
        if (!repository.existsById(id)) {
            return false;
        }
        UserDto existingUser = repository.findById(id).orElse(null);
        repository.delete(existingUser);
        return true;
    }
    public boolean existsByEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }
    public Optional<UserDto> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
