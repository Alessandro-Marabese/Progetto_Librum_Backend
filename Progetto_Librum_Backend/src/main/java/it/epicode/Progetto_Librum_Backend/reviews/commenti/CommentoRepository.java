package it.epicode.Progetto_Librum_Backend.reviews.commenti;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentoRepository extends JpaRepository<Commento, Long> {
    Page<CommentoResponse> findByReviewId(Long reviewId, Pageable pageable);
    Page<CommentoResponse> findByUtenteId(Long utenteId, Pageable pageable);
}
