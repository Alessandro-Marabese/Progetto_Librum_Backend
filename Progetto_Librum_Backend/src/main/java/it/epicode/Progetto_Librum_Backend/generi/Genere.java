package it.epicode.Progetto_Librum_Backend.generi;

import com.fasterxml.jackson.annotation.JsonBackReference;
import it.epicode.Progetto_Librum_Backend.autori.Autore;
import it.epicode.Progetto_Librum_Backend.libri.Libro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "generi")
public class Genere {
    @Id
    private String id;

    private String name;

    @ManyToMany(mappedBy = "generi")
    @JsonBackReference
    private Set<Libro> libri = new HashSet<>();

    @ManyToMany(mappedBy = "generi")
    @JsonBackReference
    private Set<Autore> autori = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genere)) return false;
        Genere genere = (Genere) o;
        return id != null && id.equals(genere.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
