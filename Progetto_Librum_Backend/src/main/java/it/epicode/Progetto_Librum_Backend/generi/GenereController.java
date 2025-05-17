package it.epicode.Progetto_Librum_Backend.generi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Page<GenereResponse> getAllGeneri(Pageable pageable) {
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
