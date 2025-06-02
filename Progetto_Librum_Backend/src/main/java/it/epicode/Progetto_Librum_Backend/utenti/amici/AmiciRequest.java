package it.epicode.Progetto_Librum_Backend.utenti.amici;

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
public class AmiciRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Utente sender;

    @ManyToOne
    private Utente receiver;

    @Enumerated(EnumType.STRING)
    private StatoRichiesta statoAmicizia;

    private LocalDate dataRichiesta = LocalDate.now();
}
