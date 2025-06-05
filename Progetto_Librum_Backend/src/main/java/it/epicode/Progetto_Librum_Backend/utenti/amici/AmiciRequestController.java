package it.epicode.Progetto_Librum_Backend.utenti.amici;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/amici")
@RequiredArgsConstructor
public class AmiciRequestController {
    private final AmiciRequestService amiciRequestService;

    @PostMapping("/sendRequest")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> sendRequest(@RequestBody AmiciRequestDTO amiciRequestDTO) {
        amiciRequestService.sendRequest(amiciRequestDTO.getSenderId(), amiciRequestDTO.getReceiverId());
        return ResponseEntity.ok("Richiesta inviata con successo");
    }

    @PutMapping("/acceptRequest/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> acceptRequest(@PathVariable Long requestId) {
        amiciRequestService.acceptRequest(requestId);
        return ResponseEntity.ok("Richiesta accettata con successo");
    }

    @PutMapping("/declineRequest/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> declineRequest(@PathVariable Long requestId) {
        amiciRequestService.rejectRequest(requestId);
        return ResponseEntity.ok("Richiesta rifiutata con successo");
    }

    @GetMapping("/requests/{receiverId}/{stato}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<AmiciRequestResponse> findAllByReceiverId(@PathVariable Long receiverId, @PathVariable StatoRichiesta stato) {
        return amiciRequestService.findAllByReceiverId(receiverId, stato);
    }
}
