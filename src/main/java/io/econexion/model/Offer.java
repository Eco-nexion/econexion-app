package io.econexion.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "ofertas")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "owner", nullable = false)
    private int offer;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "date", nullable = false)
    private String date;

//    @ManyToOne
//    @JoinColumn(name = "publication_id")
//    private Post publication;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User ofertante;

}
