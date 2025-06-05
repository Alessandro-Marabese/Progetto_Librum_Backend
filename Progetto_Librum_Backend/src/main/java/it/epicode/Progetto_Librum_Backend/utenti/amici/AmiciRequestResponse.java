package it.epicode.Progetto_Librum_Backend.utenti.amici;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmiciRequestResponse {
    private Long id;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private StatoRichiesta statoAmicizia;
    private LocalDate dataRichiesta;
}
