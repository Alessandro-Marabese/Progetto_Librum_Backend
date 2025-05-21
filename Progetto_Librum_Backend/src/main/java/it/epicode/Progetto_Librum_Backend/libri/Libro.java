package it.epicode.Progetto_Librum_Backend.libri;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.epicode.Progetto_Librum_Backend.autori.Autore;
import it.epicode.Progetto_Librum_Backend.generi.Genere;
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
@Table(name = "libri")
public class Libro {
    @Id
    private String id;

    private String titolo;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String descrizione;

    private String coverUrl;

    private Integer primoAnnoPubblicazione;

    @ManyToMany
    @JoinTable(
            name = "libro_autore",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autore_id")
    )
    @JsonManagedReference
    private Set<Autore> autori = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "libro_genere",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "genere_id")
    )
    @JsonManagedReference
    private Set<Genere> generi = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Libro)) return false;
        Libro libro = (Libro) o;
        return id != null && id.equals(libro.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
