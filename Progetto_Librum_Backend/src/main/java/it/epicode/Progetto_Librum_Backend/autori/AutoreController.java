package it.epicode.Progetto_Librum_Backend.autori;

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
@RequestMapping("/autori")
@RequiredArgsConstructor
public class AutoreController {
    private final AutoreService autoreService;

    @Transactional
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Page<AutoreResponse> findAll(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "name") String sortBy,
                                        @RequestParam(defaultValue = "asc") String direction) {
        if (!sortBy.equals("name")) {
            throw new IllegalArgumentException("Campo di ordinamento non valido: " + sortBy);
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()
        );
        return autoreService.findAll(pageable);
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Autore findById(@PathVariable String id) {
        return autoreService.findById(id);
    }

    @GetMapping("/nome/{autoreName}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public AutoreResponse findByName(@PathVariable String autoreName) {
        return autoreService.findByName(autoreName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Autore saveAutore(@RequestBody AutoreRequest autoreRequest) {
        return autoreService.saveAutore(autoreRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Autore update(@PathVariable String id, @RequestBody AutoreRequest autoreRequest) {
        return autoreService.updateAutore(id, autoreRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable String id) {
        autoreService.deleteAutore(id);
    }
}
