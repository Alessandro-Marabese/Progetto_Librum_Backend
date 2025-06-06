package it.epicode.Progetto_Librum_Backend.reviews.commenti;

import it.epicode.Progetto_Librum_Backend.exception.NotFoundException;
import it.epicode.Progetto_Librum_Backend.reviews.Review;
import it.epicode.Progetto_Librum_Backend.reviews.ReviewRepository;
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

@Service
@Validated
public class CommentoService {
    @Autowired
    private CommentoRepository commentoRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    public CommentoResponse createCommento(@Valid CommentoRequest request) {
        Commento commento = new Commento();
        BeanUtils.copyProperties(request, commento);

        Utente utente = utenteRepository.findById(request.getUtenteId())
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));

        Review review = reviewRepository.findById(request.getReviewId())
                .orElseThrow(() -> new IllegalArgumentException("Review non trovata"));

        commento.setUtente(utente);
        commento.setReview(review);
        Commento commentoSalvato = commentoRepository.save(commento);
        return fromEntity(commentoSalvato);
    }

    @Transactional
    public Page<CommentoResponse> findAllByReviewId(Long reviewId, Pageable pageable) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("Review non trovata"));
        return commentoRepository.findByReviewId(reviewId, pageable)
                .map(this::fromEntity);
    }

    @Transactional
    public Page<CommentoResponse> findAllByUtenteId(Long utenteId, Pageable pageable) {
        Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));
        return commentoRepository.findByUtenteId(utenteId, pageable)
                .map(this::fromEntity);
    }

    public void updateCommento(Long commentoId, @Valid CommentoRequest request) {
        Commento commento = commentoRepository.findById(commentoId).orElseThrow(() -> new IllegalArgumentException("Commento non trovato"));
        commento.setTesto(request.getTesto());
        commentoRepository.save(commento);
    }

    public void deleteCommento(Long commentoId) {
        boolean exists = commentoRepository.existsById(commentoId);
        if (!exists) {
            throw new NotFoundException("Commento non trovato");
        }
        commentoRepository.deleteById(commentoId);
    }

    public CommentoResponse fromEntity(Commento commento) {
        CommentoResponse commentoResponse = new CommentoResponse();
        commentoResponse.setId(commento.getId());
        commentoResponse.setTesto(commento.getTesto());
        commentoResponse.setDataCommento(commento.getDataCommento());
        commentoResponse.setUtenteId(commento.getUtente().getId());
        commentoResponse.setReviewId(commento.getReview().getId());
        return commentoResponse;
    }
}
