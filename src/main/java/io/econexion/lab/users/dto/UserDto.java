package io.econexion.lab.users.dto;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class UserDto{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(name="name", nullable=false) 
    private String name;
    
    @Column(name="email", nullable=false, unique=true)
    private String email;

    @Column(name="password", nullable=false)
    private String password;
    
    @Column(name="role")
    private String role;
    
    public UserDto(String name, String email,String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserDto() {}


    
    // Getters y Setters
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
