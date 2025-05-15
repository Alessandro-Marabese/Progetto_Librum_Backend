package it.epicode.Progetto_Librum_Backend.utenti;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Utente> findByUsername(String username);
}
