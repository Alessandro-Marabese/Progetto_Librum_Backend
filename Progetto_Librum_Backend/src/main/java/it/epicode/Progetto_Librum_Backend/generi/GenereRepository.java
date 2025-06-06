package it.epicode.Progetto_Librum_Backend.generi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface GenereRepository extends JpaRepository<Genere, String> {
    @Query("SELECT g FROM Genere g WHERE LOWER(g.name) LIKE LOWER(:name)")
    Optional<Genere> findByName(@Param("name") String genereName);
}
