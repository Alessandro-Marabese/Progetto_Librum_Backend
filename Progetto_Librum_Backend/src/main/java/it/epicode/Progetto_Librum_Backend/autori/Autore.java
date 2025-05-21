package it.epicode.Progetto_Librum_Backend.autori;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.epicode.Progetto_Librum_Backend.generi.Genere;
import it.epicode.Progetto_Librum_Backend.libri.Libro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "autori")
public class Autore {
    @Id
    private String id;

    private String name;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String bio;

    private String photoUrl;

    private String dataNascita;

    private String dataMorte;

    @ManyToMany(mappedBy = "autori")
    @JsonBackReference
    private Set<Libro> libri = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "autori_genere",
            joinColumns = @JoinColumn(name = "autore_id"),
            inverseJoinColumns = @JoinColumn(name = "genere_id")
    )
    @JsonManagedReference
    private Set<Genere> generi = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Autore)) return false;
        Autore autore = (Autore) o;
        return id != null && id.equals(autore.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


}
