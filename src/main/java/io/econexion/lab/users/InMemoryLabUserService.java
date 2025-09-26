package io.econexion.lab.users;

import io.econexion.auth.AuthenticationController;
import io.econexion.lab.users.dto.*;

import java.util.*;

import jakarta.annotation.PostConstruct;
import org.hibernate.annotations.NotFound;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;      // <-- import necesario
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile; // <-- import necesario

@Service
@Profile("lab")
public class InMemoryLabUserService {
    private final LabUserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public InMemoryLabUserService(LabUserRepository repository) throws Exception {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        try {
            UserDto newUser = new UserDto(
                    "Econexia",
                    "administrativo",
                    "123456789",
                    "admin@econexia.admin",
                    passwordEncoder.encode("admin1234"),
                    "admin"
            );
            repository.save(newUser);
        }catch (Exception e){
            System.err.println("Error al crear usuario admin: " + e.getMessage());
        }
    }

    public List<UserDto> findAll() {

        return repository.findAll();
    }

    public Optional<UserDto> findById(UUID id) throws Exception {
        if (!repository.findById(id).isPresent()) {
            throw new Exception("Usuario no encontrado");

        }
        return repository.findById(id);
    }

    public UserDto create(UserDto user) throws Exception {
        if (findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("El usuario ya existe");
        }

        return repository.save(user);
    }

    public Optional<UserDto> update(UUID id, UserDto newUser) throws Exception {
        if (!repository.findById(id).isPresent()) {
            throw new Exception("Usuario no encontrado");
        }
        newUser.setId(id);
        return Optional.of(repository.save(newUser));
    }

    public boolean delete(UUID id) throws Exception {
        if (!repository.findById(id).isPresent()) {
            throw new Exception("EL Id no esta registrado a ningun usuario");
        }
        repository.deleteById(id);
        return true;
    }
    public Optional<UserDto> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
