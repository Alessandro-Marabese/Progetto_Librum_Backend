package it.epicode.Progetto_Librum_Backend.reviews;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByLibroId(String libroId, Pageable pageable);
    Page<Review> findAllByUtenteId(Long utenteId, Pageable pageable);
}
