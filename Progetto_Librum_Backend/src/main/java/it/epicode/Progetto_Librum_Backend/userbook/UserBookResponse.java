package it.epicode.Progetto_Librum_Backend.userbook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookResponse {

    private Long utente;

    private String libro;

    private StatoLettura statoLettura;

    private LocalDate dataInizio;

    private LocalDate dataFine;
}
