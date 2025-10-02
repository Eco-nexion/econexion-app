package io.econexion.controller;

import io.econexion.model.UserDto;
import io.econexion.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lab/users")
@Profile("lab")
@Tag(name = "Usuarios en memoria", description = "Gestión de usuarios para pruebas en memoria")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos los usuarios",
            description = "Devuelve todos los usuarios almacenados en memoria",
            responses =
                    {
                            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                                    responseCode = "200",
                                    description = "si existen usuarios y retorna la lista de usuarios"
                            ),
                            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                                    responseCode = "404",
                                    description = "no se encontraron registros de usuarios en el back"
                            )
                    })

    @GetMapping("/allUsers")
    public ResponseEntity<?> list() {
        List<UserDto> salida = service.findAll();
        if (salida.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(salida);
    }

    @Operation(summary = "Obtener un usuario por ID",
            description = "Busca un usuario por su UUID y lo devuelve",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Usuario encontrado (consultar la documentacion del UserDto)"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Usuario no encontrado"
                    )
            })
    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) throws Exception {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo usuario",
            description = "Agrega un usuario en memoria y devuelve la información creada",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del usuario a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class),
                            examples = @ExampleObject(
                                    value = "{\"enterpriseName\": \"Corporation\", \"name\": \"Maria\", \"nit\": \"1236546987\", \"email\": \"maria@ejemplo.com\", \"password\": \"contraseñaSegura\",\"rol\": \"seller/buyer\" }"
                            )
                    )
            ))
    @PostMapping("/addUser")
    public ResponseEntity<UserDto> create(@RequestBody UserDto user) throws Exception {
        return ResponseEntity.ok(service.create(user));
    }

    @Operation(summary = "Actualizar un usuario",
            description = "Actualiza los datos de un usuario existente por UUID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del usuario a actualizar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class),
                            examples = @ExampleObject(
                                    value = "{\"enterpriseName\": \"Corporation\", \"name\": \"Maria\", \"nit\": \"1236546987\", \"email\": \"maria@ejemplo.com\", \"password\": \"contraseñaSegura\",\"rol\": \"seller/buyer\" }"
                            )
                    )
            ))
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UserDto user) throws Exception {
        return service.update(id, user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un usuario",
            description = "Elimina un usuario por su UUID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) throws Exception {
        return service.delete(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
