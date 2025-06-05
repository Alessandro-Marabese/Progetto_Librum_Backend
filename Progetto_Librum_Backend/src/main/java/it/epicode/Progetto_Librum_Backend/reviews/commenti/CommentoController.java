package it.epicode.Progetto_Librum_Backend.reviews.commenti;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commenti")
@RequiredArgsConstructor
public class CommentoController {
    private final CommentoService commentoService;

    @Transactional
    @GetMapping("/review/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Page<CommentoResponse>  findAllByReviewId(@PathVariable Long reviewId,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "asc") String direction) {
        Pageable pageable = PageRequest.of(page, size);
        return commentoService.findAllByReviewId(reviewId, pageable);
    }

    @Transactional
    @GetMapping("/utente/{utenteId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Page<CommentoResponse> findAllByUtenteId(@PathVariable Long utenteId,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "asc") String direction) {
        Pageable pageable = PageRequest.of(page, size);
        return commentoService.findAllByUtenteId(utenteId, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public CommentoResponse save(@Valid @RequestBody CommentoRequest commentoRequest) {
        return commentoService.createCommento(commentoRequest);
    }

    @PutMapping("/{commentoId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public void updateCommento(@PathVariable Long commentoId, @Valid @RequestBody CommentoRequest commentoRequest) {
        commentoService.updateCommento(commentoId, commentoRequest);
    }

    @DeleteMapping("/{commentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void deleteCommento(@PathVariable Long commentoId) {
        commentoService.deleteCommento(commentoId);
    }
}
