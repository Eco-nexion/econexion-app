package io.econexion.lab.users;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.econexion.lab.users.dto.UserDto;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/lab/users")
@Profile("lab")
public class LabUserController {

    private InMemoryLabUserService service;
    public LabUserController(InMemoryLabUserService service) { this.service = service; }


    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> create(@RequestBody UserDto user) {
        var created = service.create(user);
        return ResponseEntity.created(URI.create("/lab/users/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UserDto user) {
        return service.update(id, user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return service.delete(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
