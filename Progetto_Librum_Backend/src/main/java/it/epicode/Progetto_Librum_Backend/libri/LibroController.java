package it.epicode.Progetto_Librum_Backend.libri;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libri")
@RequiredArgsConstructor
public class LibroController {
    private final LibroService libroService;

    @Transactional
    @GetMapping("/autore/{autore}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Page<LibroResponse> findAllByAutore(@PathVariable String autore,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "asc") String direction) {
        Pageable pageable = PageRequest.of(page, size);
        return libroService.findAllByAutore(autore, pageable);
    }

    @Transactional
    @GetMapping("/genere/{genere}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Page<LibroResponse> findAllByGenere(@PathVariable String genere,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "asc") String direction) {
        Pageable pageable = PageRequest.of(page, size);
        return libroService.findAllByGenere(genere, pageable);
    }

    @Transactional
    @GetMapping("/titolo/{titolo}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Page<LibroResponse> findAllByTitolo(@PathVariable String titolo,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "asc") String direction) {
        Pageable pageable = PageRequest.of(page, size);
        return libroService.findAllByTitolo(titolo, pageable);
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Libro findById(@PathVariable String id) {
        return libroService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteLibro(@PathVariable String id) {
        libroService.deleteLibro(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Libro updateLibro(@PathVariable String id, @RequestBody LibroRequest request) {
        return libroService.update(request, id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Libro saveNewLibro(@RequestBody LibroRequest request) {
        return libroService.save(request);
    }
}
