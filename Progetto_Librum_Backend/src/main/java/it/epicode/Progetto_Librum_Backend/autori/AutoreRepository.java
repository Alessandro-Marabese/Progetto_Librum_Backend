package it.epicode.Progetto_Librum_Backend.autori;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutoreRepository extends JpaRepository<Autore, String> {
    boolean existsByName(String autoreName);
    boolean existsById(String id);
    Optional<Autore> findByName(String autoreName);
}
