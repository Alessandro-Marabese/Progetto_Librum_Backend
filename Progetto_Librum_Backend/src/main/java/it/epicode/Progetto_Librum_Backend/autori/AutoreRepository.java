package it.epicode.Progetto_Librum_Backend.autori;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AutoreRepository extends JpaRepository<Autore, String> {
    boolean existsByName(String autoreName);
    boolean existsById(String id);
    @Query("SELECT a FROM Autore a WHERE LOWER(a.name) LIKE LOWER(:name)")
    Optional<Autore> findByName(@Param("name") String autoreName);
}
