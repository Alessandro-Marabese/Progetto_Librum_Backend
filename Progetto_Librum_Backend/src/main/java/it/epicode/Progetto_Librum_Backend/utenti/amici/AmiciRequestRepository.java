package it.epicode.Progetto_Librum_Backend.utenti.amici;

import it.epicode.Progetto_Librum_Backend.utenti.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AmiciRequestRepository extends JpaRepository<AmiciRequest, Long> {
    List<AmiciRequest> findByReceiverAndStatoAmicizia(Utente receiver, StatoRichiesta stato);
    Optional<AmiciRequest> findBySenderAndReceiver(Utente sender, Utente receiver);
}
