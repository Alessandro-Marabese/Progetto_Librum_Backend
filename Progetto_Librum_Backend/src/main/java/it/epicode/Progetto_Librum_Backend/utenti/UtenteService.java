package it.epicode.Progetto_Librum_Backend.utenti;

import it.epicode.Progetto_Librum_Backend.auth.JwtTokenUtil;
import it.epicode.Progetto_Librum_Backend.auth.Role;
import it.epicode.Progetto_Librum_Backend.cloudinary.CloudinaryService;
import it.epicode.Progetto_Librum_Backend.common.CommonResponse;
import it.epicode.Progetto_Librum_Backend.exception.NotFoundException;
import it.epicode.Progetto_Librum_Backend.exception.UsernameException;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
@Validated
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public UtenteResponse fromEntity(Utente utente) {
        UtenteResponse utenteResponse = new UtenteResponse();
        utenteResponse.setId(utente.getId());
        utenteResponse.setNome(utente.getNome());
        utenteResponse.setCognome(utente.getCognome());
        utenteResponse.setEmail(utente.getEmail());
        utenteResponse.setUsername(utente.getUsername());
        utenteResponse.setRoles(utente.getRoles());
        utenteResponse.setAvatar(utente.getAvatar());
        return utenteResponse;
    }

    public void registerUtente(UtenteAuthRequest request) throws MessagingException {
        if(utenteRepository.existsByUsername(request.getUsername())) {
            throw new EntityExistsException("Username già in uso");
        }
        Utente utente = new Utente();
        utente.setNome(request.getNome());
        utente.setCognome(request.getCognome());
        utente.setEmail(request.getEmail());
        utente.setUsername(request.getUsername());
        utente.setPassword(passwordEncoder.encode(request.getPassword()));
        utente.setRoles(Set.of(Role.ROLE_USER));
        utente.setAvatar("https://ui-avatars.com/api/?name=" + utente.getNome() + "+" + utente.getCognome());
        utenteRepository.save(utente);
    }

    public void logout() {

    }

    public Page<UtenteResponse> searchUsers(String query, Pageable pageable) {
        Page<Utente> users = utenteRepository
                .findByUsernameContainingIgnoreCaseOrNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(
                        query, query, query, pageable
                );

        return users.map(this::fromEntity);
    }

    public String authenticateUser(String username, String password)  {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            throw new SecurityException("Credenziali non valide", e);
        }
    }


    public CommonResponse createUtente(UtenteRequest request) throws MessagingException {
        Utente utente = new Utente();
        BeanUtils.copyProperties(request, utente);
        if (utenteRepository.existsByUsername(utente.getUsername())) {
            throw new UsernameException("Username già in uso");
        }
        if (utenteRepository.existsByEmail(utente.getEmail())) {
            throw new UsernameException("Email già in uso");
        }
        utente.setAvatar("https://ui-avatars.com/api/?name=" + utente.getNome() + "+" + utente.getCognome());
        utente = utenteRepository.save(utente);
        return new CommonResponse(utente.getId());
    }

    public Page<Utente> findAll(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return utenteRepository.findAll(pageable);
    }

    public UtenteResponse getUtenteById(Long id) {
        Utente utente = utenteRepository.findById(id).orElseThrow(() -> new NotFoundException("Utente non trovato"));
        return fromEntity(utente);
    }

    public UtenteResponse updateUtente(Long id, UtenteRequest request, Utente  utenteCorrente) {

            Utente utente = utenteRepository.findById(id).orElseThrow(() -> new NotFoundException("Utente non trovato"));
            BeanUtils.copyProperties(request, utente);
        Utente updated = utenteRepository.save(utente);
        return fromEntity(updated);
    }
    public void deleteUtente(Long id) {
        if (!utenteRepository.existsById(id)) {
            throw new NotFoundException("Utente non trovato");
        }
        utenteRepository.deleteById(id);
    }
    public boolean existsByUsername(String username) {
        return utenteRepository.existsByUsername(username);
    }
    public void uploadImage(Long id, MultipartFile file) {
        Utente utente = utenteRepository.findById(id).orElseThrow(() -> new NotFoundException("Utente non trovato"));
        utente.setAvatar(cloudinaryService.uploadImage(file));
        utenteRepository.save(utente);
    }

    public Page<UtenteResponse> getAmici(Long utenteId, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));
        return utenteRepository.findAmiciById(utenteId, pageable).map(this::fromEntity);
    }

    public void deleteFriend(Long utenteId, Long amicoId) {
        Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));
        Utente amico = utenteRepository.findById(amicoId).orElseThrow(() -> new IllegalArgumentException("Amico non trovato"));

        utente.getAmici().remove(amico);
        amico.getAmici().remove(utente);

        utenteRepository.save(utente);
        utenteRepository.save(amico);
    }
}
