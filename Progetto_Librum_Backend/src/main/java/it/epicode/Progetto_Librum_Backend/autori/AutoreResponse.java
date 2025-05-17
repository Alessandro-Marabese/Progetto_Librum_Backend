package it.epicode.Progetto_Librum_Backend.autori;

import it.epicode.Progetto_Librum_Backend.generi.GenereResponse;
import it.epicode.Progetto_Librum_Backend.libri.LibroResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoreResponse {
    private String id;
    private String name;
    private String bio;
    private String photoUrl;
    private String dataNascita;
    private String dataMorte;

    private Set<String> generi;
    private Set<String> libri;
}
