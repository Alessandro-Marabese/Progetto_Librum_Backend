package it.epicode.Progetto_Librum_Backend.generi;

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
@RequestMapping("/generi")
@RequiredArgsConstructor
public class GenereController {
    private final GenereService genereService;

    @GetMapping("/{genereName}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Genere getGenereByName(@PathVariable String genereName) {
        return genereService.findGenereByName(genereName);
    }

    @Transactional
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Page<GenereResponse> getAllGeneri( @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "100") int size,
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
        return genereService.findAllGeneri(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Genere saveGenere(@RequestBody GenereRequest request) {
        return genereService.saveGenere(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Genere updateGenere(@PathVariable String id, @RequestBody GenereRequest request) {
        return genereService.updateGenere(request, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteGenere(@PathVariable String id) {
        genereService.deleteGenere(id);
    }
}
