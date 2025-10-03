package io.econexion.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "messages")
@Getter
@Setter
public class Message {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "owner", nullable = false)
    private String owner;


    @Column(name = "date", nullable = false)
    private String date;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User sender;
}
