package it.epicode.Progetto_Librum_Backend.utenti.amici;

import it.epicode.Progetto_Librum_Backend.utenti.Utente;
import it.epicode.Progetto_Librum_Backend.utenti.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Service
@Validated
public class AmiciRequestService {
    @Autowired
    private AmiciRequestRepository amiciRequestRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    public void sendRequest (Long senderId, Long receiverId) {
        Utente sender = utenteRepository.findById(senderId).orElseThrow(() -> new IllegalArgumentException("Sender non trovato"));
        Utente receiver = utenteRepository.findById(receiverId).orElseThrow(() -> new IllegalArgumentException("Receiver non trovato"));

        if(amiciRequestRepository.findBySenderAndReceiver(sender, receiver).isPresent()) {
            throw new IllegalArgumentException("Richiesta già inviata");
        }

        AmiciRequest request = new AmiciRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatoAmicizia(StatoRichiesta.PENDENTE);

        amiciRequestRepository.save(request);
    }

    public void acceptRequest(Long requestId) {
        AmiciRequest request = amiciRequestRepository.findById(requestId).orElseThrow(() -> new IllegalArgumentException("Richiesta non trovata"));
        if (request.getStatoAmicizia() != StatoRichiesta.PENDENTE) {
            throw new IllegalStateException("La richiesta non è pendente.");
        }
        Utente sender = request.getSender();
        Utente receiver = request.getReceiver();

        sender.getAmici().add(receiver);
        receiver.getAmici().add(sender);

        utenteRepository.save(sender);
        utenteRepository.save(receiver);

        request.setStatoAmicizia(StatoRichiesta.ACCETTATA);
        amiciRequestRepository.save(request);
    }

    public void rejectRequest(Long requestId) {
        AmiciRequest request = amiciRequestRepository.findById(requestId).orElseThrow(() -> new IllegalArgumentException("Richiesta non trovata"));
        request.setStatoAmicizia(StatoRichiesta.RIFIUTATA);
        amiciRequestRepository.save(request);
    }


}
