package it.epicode.Progetto_Librum_Backend.libri;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LibroRepository extends JpaRepository<Libro, String> {
    boolean existsById(String id);

    @Query("SELECT l FROM Libro l JOIN l.autori a WHERE LOWER(a.name) LIKE LOWER(:autore) ORDER BY a.name ASC")
    Page<Libro> findAllByAutoreNameContainingOrderByAutoreNameAsc(@Param("autore") String autore, Pageable pageable);

    @Query("SELECT l FROM Libro l JOIN l.autori a WHERE a.id = :autoreId ORDER BY l.titolo ASC")
    Page<Libro> findAllByAutoreId(@Param("autoreId") String autoreId, Pageable pageable);

    @Query("SELECT l FROM Libro l JOIN l.generi g WHERE LOWER(g.name) LIKE LOWER(:genere) ORDER BY g.name ASC")
    Page<Libro> findAllByGeneriNameContainingOrderByGenereNameAsc(@Param("genere") String genere, Pageable pageable);
    @Query("SELECT l FROM Libro l WHERE LOWER(l.titolo) LIKE LOWER(:titolo) ORDER BY l.titolo ASC")
    Page<Libro> findAllByTitoloContainingOrderByTitoloAsc(@Param("titolo") String titolo, Pageable pageable);
}
