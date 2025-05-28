package it.epicode.Progetto_Librum_Backend.userbook;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookRequest {
    @NotNull(message = "L'utente non può essere nullo")
    private Long userId;
    @NotBlank(message = "Il libro non può essere nullo")
    private String bookId;
    @NotNull(message = "Lo stato di lettura non può essere nullo")
    private StatoLettura statoLettura;

}
