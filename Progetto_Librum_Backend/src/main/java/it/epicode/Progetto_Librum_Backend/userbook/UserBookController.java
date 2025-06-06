package it.epicode.Progetto_Librum_Backend.userbook;

import it.epicode.Progetto_Librum_Backend.libri.Libro;
import it.epicode.Progetto_Librum_Backend.libri.LibroResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userbook")
public class UserBookController {
    private final UserBookService userBookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public void createUserBook(@RequestBody UserBookRequest request) {
         userBookService.addBookToUser(request.getUserId(), request.getBookId(), request.getStatoLettura());
    }

    @Transactional
    @GetMapping("/{utenteId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<UserBookResponse> getAllByUtenteId(@PathVariable("utenteId") Long userId) {
        return userBookService.findAllByUtenteId(userId);
    }

    @Transactional
    @PutMapping("/{utenteId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public void updateStatoLettura(@PathVariable Long utenteId,@RequestBody UpdateStatoLetturaDTO dto) {
        userBookService.updateStatoLettura(utenteId, dto.getLibroId(), dto.getNuovoStato());
    }

    @DeleteMapping("/{utenteId}/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void removeBookFromUser(@PathVariable("utenteId") Long utenteId, @RequestParam("bookId") String bookId) {
        userBookService.deleteBookFromUser(utenteId, bookId);
    }
}
