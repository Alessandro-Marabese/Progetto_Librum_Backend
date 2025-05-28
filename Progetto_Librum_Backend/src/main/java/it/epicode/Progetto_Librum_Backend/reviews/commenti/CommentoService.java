package it.epicode.Progetto_Librum_Backend.reviews.commenti;

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

    public Commento createCommento(@Valid CommentoRequest request) {
        Commento commento = new Commento();
        BeanUtils.copyProperties(request, commento);
        return commentoRepository.save(commento);
    }

    public Page<CommentoResponse> findAllByReviewId(Long reviewId, Pageable pageable) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("Review not found"));
        return commentoRepository.findByReviewId(reviewId, pageable);
    }

    public Page<CommentoResponse> findAllByUtenteId(Long utenteId, Pageable pageable) {
        Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new IllegalArgumentException("Utente not found"));
        return commentoRepository.findByUtenteId(utenteId, pageable);
    }

    public void deleteCommento(Long commentoId) {
        commentoRepository.deleteById(commentoId);
    }
}
