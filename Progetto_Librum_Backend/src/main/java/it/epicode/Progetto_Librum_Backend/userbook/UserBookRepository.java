package it.epicode.Progetto_Librum_Backend.userbook;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook, UserBookId> {
    List<UserBook> findByUtenteId(Long utenteId);
    List<UserBook> findByUtenteIdAndStatoLettura(Long utenteId, StatoLettura statoLettura);
}
