package it.epicode.Progetto_Librum_Backend.generi;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenereRequest {
    @NotBlank(message = "L'id non può essere vuoto")
    private String id;
    @NotBlank(message = "Il nome non può essere vuoto")
    private String name;
}
