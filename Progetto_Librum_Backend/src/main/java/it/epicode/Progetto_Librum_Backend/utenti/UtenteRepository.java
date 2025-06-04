package it.epicode.Progetto_Librum_Backend.utenti;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UtenteRepository extends JpaRepository<Utente, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Page<Utente> findByUsernameContainingIgnoreCaseOrNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(
            String username, String nome, String cognome, Pageable pageable
    );
    Optional<Utente> findByUsername(String username);
    @Query("SELECT a FROM Utente u JOIN u.amici a WHERE u.id = :id")
    Page<Utente> findAmiciById(@Param("id") Long id, Pageable pageable);
}
