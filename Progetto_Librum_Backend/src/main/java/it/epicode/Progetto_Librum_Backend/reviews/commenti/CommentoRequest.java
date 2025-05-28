package it.epicode.Progetto_Librum_Backend.reviews.commenti;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentoRequest {
    @NotBlank(message = "Il testo non può essere vuoto")
    private String testo;
    @NotNull(message = "L'utente non può essere nullo")
    private Long utenteId;
    @NotNull(message = "La review non può essere nullo")
    private Long reviewId;
}
