package it.epicode.Progetto_Librum_Backend.reviews;

import it.epicode.Progetto_Librum_Backend.libri.Libro;
import it.epicode.Progetto_Librum_Backend.libri.LibroRepository;
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
public class ReviewRunner implements CommandLineRunner {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    private final Random random = new Random();

    private final List<String> commenti = List.of(
            "Bellissimo libro!",
            "Mi ha lasciato senza parole.",
            "Un po' lento all'inizio, ma poi migliora.",
            "Non è il mio genere.",
            "Scritto molto bene, lo consiglio.",
            "Una lettura piacevole.",
            "Pensavo meglio, ma comunque ok.",
            "Super interessante!",
            "Da leggere tutto d'un fiato.",
            "Lo rileggerei volentieri."
    );

    @Override
    public void run(String... args) throws Exception {
        /*List<Libro> libri = libroRepository.findAll();
        List<Utente> utenti = utenteRepository.findAll();

        if (libri.isEmpty() || utenti.isEmpty()) {
            System.out.println("Nessun libro o utente trovato.");
            return;
        }

        for (Libro libro : libri) {
            for (int i = 0; i < 3; i++) {
                Utente utenteRandom = utenti.get(random.nextInt(utenti.size()));

                Review review = new Review();
                review.setLibro(libro);
                review.setUtente(utenteRandom);
                review.setRating(random.nextInt(5) + 1); // Rating da 1 a 5
                review.setCommento(commenti.get(random.nextInt(commenti.size())));

                reviewRepository.save(review);
            }
        }
        System.out.println("Review fittizie generate con successo!");*/
    }
}
