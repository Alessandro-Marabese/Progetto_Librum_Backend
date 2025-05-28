package it.epicode.Progetto_Librum_Backend.reviews;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    @NotBlank(message = "Il commento non può essere vuoto")
    private String comment;
    @NotNull(message = "Il rating non può essere nullo")
    private int rating;

    @NotNull(message = "L'utente non può essere nullo")
    private Long utenteId;
    @NotNull(message = "Il libro non può essere nullo")
    private String libroId;
}
