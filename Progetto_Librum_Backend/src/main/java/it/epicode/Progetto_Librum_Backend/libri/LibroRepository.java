package it.epicode.Progetto_Librum_Backend.libri;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LibroRepository extends JpaRepository<Libro, String> {
    boolean existsById(String id);
    Page<Libro> findByAutoreName(String autoreName, Pageable pageable);

    Page<Libro> findByGenereName(String genereName, Pageable pageable);
    Page<Libro> findByTitle(String title, Pageable pageable);
}
