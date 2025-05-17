package it.epicode.Progetto_Librum_Backend.autori;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoreRequest {
    @NotBlank(message = "L'id non può essere vuoto")
    private String id;
    @NotBlank(message = "Il nome non può essere vuoto")
    private String name;
    private String bio;
    private String photoUrl;
    private String dataNascita;
    private String dataMorte;
    private Set<String> generiIds;
}
