package it.epicode.Progetto_Librum_Backend.reviews.commenti;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentoRepository extends JpaRepository<Commento, Long> {
    boolean existsById(Long id);
    Page<Commento> findByReviewId(Long reviewId, Pageable pageable);
    Page<Commento> findByUtenteId(Long utenteId, Pageable pageable);
}
