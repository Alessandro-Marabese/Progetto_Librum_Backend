package it.epicode.Progetto_Librum_Backend.userbook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatoLetturaDTO {
        private String libroId;
        private StatoLettura nuovoStato;
}
