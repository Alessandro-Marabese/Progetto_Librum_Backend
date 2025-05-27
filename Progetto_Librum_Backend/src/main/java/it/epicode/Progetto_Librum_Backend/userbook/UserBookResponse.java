package it.epicode.Progetto_Librum_Backend.userbook;

import it.epicode.Progetto_Librum_Backend.libri.LibroResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookResponse {

    private Long utente;

    private LibroResponse libro;

    private StatoLettura statoLettura;

    private LocalDate dataInizio;

    private LocalDate dataFine;
}
