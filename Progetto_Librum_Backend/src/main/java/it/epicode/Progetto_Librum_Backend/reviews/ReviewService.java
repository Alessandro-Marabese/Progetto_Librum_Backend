package it.epicode.Progetto_Librum_Backend.reviews;

import it.epicode.Progetto_Librum_Backend.autori.Autore;
import it.epicode.Progetto_Librum_Backend.exception.NotFoundException;
import it.epicode.Progetto_Librum_Backend.libri.Libro;
import it.epicode.Progetto_Librum_Backend.libri.LibroRepository;
import it.epicode.Progetto_Librum_Backend.libri.LibroResponse;
import it.epicode.Progetto_Librum_Backend.utenti.Utente;
import it.epicode.Progetto_Librum_Backend.utenti.UtenteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.stream.Collectors;


@Service
@Validated
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    @Transactional
    public Page<ReviewResponse> findAllByLibro(String libroId, Pageable pageable) {
        libroId = libroId.replace("\"", "");
        Libro libro = libroRepository.findById(libroId).orElseThrow(() -> new NotFoundException("Libro non trovato"));
        return reviewRepository.findAllByLibroId(libroId, pageable)
                .map(this::fromEntity);
    }

    @Transactional
    public Page<ReviewResponse> findAllByUtente(Long utenteId, Pageable pageable) {
        Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException("Utente non trovato"));
        return reviewRepository.findAllByUtenteId(utenteId, pageable)
                .map(this::fromEntity);
    }

    public Review createReview(@Valid ReviewRequest reviewRequest) {
        Review review = new Review();
        BeanUtils.copyProperties(reviewRequest, review);
        Utente utente = utenteRepository.findById(reviewRequest.getUtenteId())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Libro libro = libroRepository.findById(reviewRequest.getLibroId())
                .orElseThrow(() -> new RuntimeException("Libro non trovato"));

        review.setUtente(utente);
        review.setLibro(libro);
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public ReviewResponse updateReview(Long id, @Valid ReviewRequest reviewRequest) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review non trovata"));
        BeanUtils.copyProperties(reviewRequest, review);
        return fromEntity(reviewRepository.save(review));
    }

    public ReviewResponse fromEntity(Review review) {
        Libro libro = review.getLibro();
        LibroResponse libroDTO = new LibroResponse();
        libroDTO.setId(libro.getId());
        libroDTO.setTitolo(libro.getTitolo());
        libroDTO.setCoverUrl(libro.getCoverUrl());
        libroDTO.setNomiAutori(libro.getAutori().stream().map(Autore::getName).collect(Collectors.toSet()));

        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setId(review.getId());
        reviewResponse.setCommento(review.getCommento());
        reviewResponse.setRating(review.getRating());
        reviewResponse.setDataCreazione(review.getDataCreazione());
        reviewResponse.setUtenteId(review.getUtente().getId());
        reviewResponse.setLibro(libroDTO);
        return reviewResponse;
    }
}
