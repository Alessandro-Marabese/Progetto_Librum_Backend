package it.epicode.Progetto_Librum_Backend.generi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenereResponse {
    private String id;
    private String name;
    private Set<String> libri;
    private Set<String> autori;
}
