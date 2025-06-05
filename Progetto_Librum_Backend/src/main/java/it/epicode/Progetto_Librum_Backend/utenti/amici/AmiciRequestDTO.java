package it.epicode.Progetto_Librum_Backend.utenti.amici;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmiciRequestDTO {
    private Long senderId;
    private Long receiverId;
}
