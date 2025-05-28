package it.epicode.Progetto_Librum_Backend.reviews.commenti;

import it.epicode.Progetto_Librum_Backend.reviews.Review;
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
@Table(name = "commenti")
public class Commento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String testo;

    private LocalDate dataCommento;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @PrePersist
    public void prePersist() {
        dataCommento = LocalDate.now();
    }
}
