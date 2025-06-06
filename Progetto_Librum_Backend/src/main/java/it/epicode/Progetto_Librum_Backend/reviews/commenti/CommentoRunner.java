package it.epicode.Progetto_Librum_Backend.reviews.commenti;

import it.epicode.Progetto_Librum_Backend.reviews.Review;
import it.epicode.Progetto_Librum_Backend.reviews.ReviewRepository;
import it.epicode.Progetto_Librum_Backend.utenti.Utente;
import it.epicode.Progetto_Librum_Backend.utenti.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class CommentoRunner implements CommandLineRunner {
    @Autowired
    private CommentoRepository commentoRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    private final Random random = new Random();

    private final List<String> commentiAlleReview = List.of(
            "Concordo pienamente con la tua opinione!",
            "Interessante punto di vista, non ci avevo pensato.",
            "Non sono d'accordo, ma rispetto la tua opinione.",
            "Hai colto aspetti che mi erano sfuggiti, grazie!",
            "Recensione ben scritta, mi ha incuriosito.",
            "Mi hai convinto a leggere questo libro.",
            "Non mi è piaciuto quanto a te, ma apprezzo la tua analisi.",
            "Hai evidenziato punti deboli che condivido.",
            "La tua recensione è stata molto utile, grazie!",
            "Hai espresso bene le tue emozioni durante la lettura.",
            "Recensione dettagliata e coinvolgente, complimenti!",
            "Mi ha fatto riflettere su aspetti che non avevo considerato.",
            "Hai trasmesso la tua passione per il libro, bello da leggere.",
            "Non condivido tutto, ma apprezzo la tua sincerità.",
            "Recensione equilibrata e ben argomentata.",
            "Hai evidenziato aspetti che mi hanno fatto rivalutare il libro.",
            "Grazie per la tua recensione, mi ha aiutato nella scelta.",
            "Hai analizzato bene i personaggi, interessante.",
            "Recensione chiara e concisa, molto utile.",
            "Mi piace come hai strutturato la tua opinione."
    );

    @Override
    public void run(String... args) throws Exception {
        /*List<Review> reviews = reviewRepository.findAll();
        List<Utente> utenti = utenteRepository.findAll();


        if (reviews.isEmpty() || utenti.isEmpty()) {
            System.out.println("Nessuna review o utente trovati.");
            return;
        }

        for(Review review : reviews) {
            for (int i = 0; i < 3; i++) {
                Utente utenteRandom = utenti.get(random.nextInt(utenti.size()));
                if (utenteRandom.getId().equals(review.getUtente().getId())) continue;

                Commento commento = new Commento();
                commento.setReview(review);
                commento.setUtente(utenteRandom);
                commento.setTesto(commentiAlleReview.get(random.nextInt(commentiAlleReview.size())));
                commentoRepository.save(commento);

            }
        }
        System.out.println("Commenti finti alle review generati con successo!");*/
    }
}
