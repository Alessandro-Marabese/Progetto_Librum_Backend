package it.epicode.Progetto_Librum_Backend.reviews.commenti;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentoRequest {
    @NotBlank(message = "Il testo non può essere vuoto")
    private String testo;
}
