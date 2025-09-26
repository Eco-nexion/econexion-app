package io.econexion.lab.users.dto;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class UserDto{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name="enterprise name", nullable=true)
    private String enterpriseName;

    @Column(name="name", nullable=false)
    private String username;

    @Column(name="nit")
    private String nit;


    @Column(name="email", nullable=false, unique=true)
    private String email;

    @Column(name="password", nullable=false)
    private String password;

    @Column(name="rol")
    private String rol;

    public UserDto(String enterpriseName, String username, String nit, String email, String password, String rol) {
        this.enterpriseName = enterpriseName;
        this.username = username;
        this.nit = nit;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }
    public UserDto() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
