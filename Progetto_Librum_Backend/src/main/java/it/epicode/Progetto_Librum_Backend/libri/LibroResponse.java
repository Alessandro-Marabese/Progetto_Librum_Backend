package it.epicode.Progetto_Librum_Backend.libri;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroResponse {

    private String id;
    private String titolo;
    private String descrizione;
    private String coverUrl;
    private Integer primoAnnoPubblicazione;

    private Set<String> nomiAutori;
    private Set<String> nomiGeneri;


}
