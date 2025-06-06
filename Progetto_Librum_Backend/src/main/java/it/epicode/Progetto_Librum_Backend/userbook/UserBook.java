package it.epicode.Progetto_Librum_Backend.userbook;

import it.epicode.Progetto_Librum_Backend.libri.Libro;
import it.epicode.Progetto_Librum_Backend.utenti.Utente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userbook")
public class UserBook {
    @EmbeddedId
    private UserBookId id = new UserBookId();

    @ManyToOne
    @MapsId("utenteId")
    private Utente utente;

    @ManyToOne
    @MapsId("libroId")
    private Libro libro;

    @Enumerated(EnumType.STRING)
    private StatoLettura statoLettura;

    private LocalDate dataInizio;
    private LocalDate dataFine;
}
