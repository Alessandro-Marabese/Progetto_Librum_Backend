package it.epicode.Progetto_Librum_Backend.reviews;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private String commento;
    private Integer rating;
    private LocalDate dataCreazione;
    private Long utenteId;
    private String libroId;
}
