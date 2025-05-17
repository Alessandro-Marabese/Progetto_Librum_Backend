package it.epicode.Progetto_Librum_Backend.generi;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface GenereRepository extends JpaRepository<Genere, String> {
    Optional<Genere> findByName(String genereName);
}
