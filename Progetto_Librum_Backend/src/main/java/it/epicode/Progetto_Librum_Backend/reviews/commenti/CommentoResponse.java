package it.epicode.Progetto_Librum_Backend.reviews.commenti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentoResponse {
    private String testo;
    private LocalDate dataCommento;
    private Long utenteId;
    private Long reviewId;
}
