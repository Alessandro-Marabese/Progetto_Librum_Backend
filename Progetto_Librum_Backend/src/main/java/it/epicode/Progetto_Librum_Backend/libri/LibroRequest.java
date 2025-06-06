package it.epicode.Progetto_Librum_Backend.libri;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroRequest {
    @NotBlank(message = "L'id non può essere vuoto")
    private String id;
    @NotBlank(message = "Il titolo non può essere vuoto")
    private String titolo;
    private String descrizione;
    @NotNull(message = "Il primo anno di pubblicazione non può essere vuoto")
    private Integer primoAnnoPubblicazione;
    @NotBlank(message = "La coverUrl non può essere vuota")
    private String coverUrl;

    @NotNull(message = "L'elenco degli ID autori non può essere nullo")
    private Set<String> autoriIds;

    @NotNull(message = "L'elenco degli ID generi non può essere nullo")
    private Set<String> generiIds;
}
