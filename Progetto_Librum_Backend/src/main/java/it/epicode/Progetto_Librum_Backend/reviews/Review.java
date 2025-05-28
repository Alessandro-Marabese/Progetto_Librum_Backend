package it.epicode.Progetto_Librum_Backend.reviews;

import it.epicode.Progetto_Librum_Backend.libri.Libro;
import it.epicode.Progetto_Librum_Backend.reviews.commenti.Commento;
import it.epicode.Progetto_Librum_Backend.utenti.Utente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "review")
    private List<Commento> commenti;

    @PrePersist
    public void prePersist() {
        dataCreazione = LocalDate.now();
    }
}
