package it.epicode.Progetto_Librum_Backend.utenti;

import it.epicode.Progetto_Librum_Backend.auth.AuthResponse;
import it.epicode.Progetto_Librum_Backend.auth.LoginRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/utenti")
public class UtenteController {
    @Autowired
    private UtenteService utenteService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Page<Utente> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {

        return utenteService.findAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UtenteResponse findById(@PathVariable Long id) {
        return utenteService.getUtenteById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/current-user")
    public UtenteResponse getCurrentUser(@AuthenticationPrincipal Utente utente) {
        return utenteService.fromEntity(utente);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerUtente(@RequestBody UtenteAuthRequest request) throws MessagingException {
        utenteService.registerUtente(request);
        return ResponseEntity.ok("Registrazione avvenuta con successo");
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Utente updateUtente(@PathVariable Long id, @RequestBody UtenteRequest request, @AuthenticationPrincipal Utente utente) {
        return utenteService.updateUtente(id, request, utente);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUtente(@PathVariable Long id) {
        utenteService.deleteUtente(id);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping(value = "/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UtenteResponse uploadImage(@PathVariable Long id, @RequestPart MultipartFile file) {
        utenteService.uploadImage(id, file);
        return utenteService.getUtenteById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login request:");
        String token = utenteService.authenticateUser(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
