package it.epicode.Progetto_Librum_Backend.reviews;

import it.epicode.Progetto_Librum_Backend.libri.Libro;
import it.epicode.Progetto_Librum_Backend.utenti.Utente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String commento;

    private Integer rating;

    private LocalDate dataCreazione;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    @PrePersist
    public void prePersist() {
        dataCreazione = LocalDate.now();
    }
}
