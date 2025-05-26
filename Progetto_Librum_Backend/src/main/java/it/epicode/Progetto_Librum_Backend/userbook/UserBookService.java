package it.epicode.Progetto_Librum_Backend.userbook;

import it.epicode.Progetto_Librum_Backend.autori.Autore;
import it.epicode.Progetto_Librum_Backend.exception.NotFoundException;
import it.epicode.Progetto_Librum_Backend.generi.Genere;
import it.epicode.Progetto_Librum_Backend.libri.Libro;
import it.epicode.Progetto_Librum_Backend.libri.LibroRepository;
import it.epicode.Progetto_Librum_Backend.libri.LibroResponse;
import it.epicode.Progetto_Librum_Backend.utenti.Utente;
import it.epicode.Progetto_Librum_Backend.utenti.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class UserBookService {
    private final UserBookRepository userBookRepository;
    private final LibroRepository libroRepository;
    private final UtenteRepository utenteRepository;


    public void addBookToUser(Long utenteId, String libroId, StatoLettura statoLettura) {
        Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException("Utente non trovato"));
        Libro libro = libroRepository.findById(libroId).orElseThrow(() -> new NotFoundException("Libro non trovato"));

        if (userBookRepository.existsById(new UserBookId(utenteId, libroId))) {
            throw new NotFoundException("Libro già inserito");
        }

        UserBook userBook = new UserBook();
        userBook.setId(new UserBookId(utenteId, libroId));
        userBook.setUtente(utente);
        userBook.setLibro(libro);
        userBook.setStatoLettura(statoLettura);
        userBookRepository.save(userBook);
    }

    @Transactional
    public List<LibroResponse> findAllByUtenteId(Long utenteId) {
        return userBookRepository.findByUtenteId(utenteId)
                .stream()
                .map(UserBook::getLibro)
                .map(this::fromEntityLibro)
                .toList();
    }

    public void deleteBookFromUser(Long utenteId, String libroId) {
        libroId = libroId.replace("\"", "");
        UserBookId userBookId = new UserBookId(utenteId, libroId);
        boolean exists = userBookRepository.existsById(userBookId);
        if (exists) {
            userBookRepository.deleteById(userBookId);
        } else {
            throw new NotFoundException("Libro non trovato");
        }
    }

    public UserBookResponse fromEntity(UserBook userBook) {
        UserBookResponse userBookResponse = new UserBookResponse();
        userBookResponse.setUtente(userBook.getUtente().getId());
        userBookResponse.setLibro(userBook.getLibro().getId());
        userBookResponse.setStatoLettura(userBook.getStatoLettura());
        userBookResponse.setDataInizio(userBook.getDataInizio());
        userBookResponse.setDataFine(userBook.getDataFine());
        return userBookResponse;
    }

    public LibroResponse fromEntityLibro(Libro libro) {
        LibroResponse libroResponse = new LibroResponse();
        libroResponse.setId(libro.getId());
        libroResponse.setTitolo(libro.getTitolo());
        libroResponse.setDescrizione(libro.getDescrizione());
        libroResponse.setCoverUrl(libro.getCoverUrl());
        libroResponse.setPrimoAnnoPubblicazione(libro.getPrimoAnnoPubblicazione());

        if(libro.getAutori() != null) {
            libroResponse.setNomiAutori(
                    libro.getAutori().stream()
                            .map(Autore::getName)
                            .collect(Collectors.toSet())
            );
        }
        if(libro.getGeneri() != null) {
            libroResponse.setNomiGeneri(
                    libro.getGeneri().stream()
                            .map(Genere::getName)
                            .collect(Collectors.toSet()));
        }
        return libroResponse;
    }
}
