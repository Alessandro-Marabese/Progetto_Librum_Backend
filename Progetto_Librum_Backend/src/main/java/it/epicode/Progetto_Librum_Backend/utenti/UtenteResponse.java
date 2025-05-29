package it.epicode.Progetto_Librum_Backend.utenti;

import it.epicode.Progetto_Librum_Backend.auth.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtenteResponse {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String username;
    private String avatar;
    private Set<Role> roles;
}
