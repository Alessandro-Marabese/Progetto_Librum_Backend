package it.epicode.Progetto_Librum_Backend.utenti;

import com.github.javafaker.Faker;
import it.epicode.Progetto_Librum_Backend.auth.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CreaUtentiRunner implements CommandLineRunner {
    @Autowired
    Faker faker;
    @Autowired
    UtenteRepository utenteRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++) {
            Utente utente = new Utente();
            utente.setNome(faker.name().firstName());
            utente.setCognome(faker.name().lastName());
            utente.setEmail(utente.getNome() + "." + utente.getCognome() + "@gmail.com");
            utente.setUsername((utente.getNome() + "." + utente.getCognome()).toLowerCase().replaceAll("[^a-z0-9.]", ""));
            utente.setAvatar("https://ui-avatars.com/api/?name=" + utente.getNome() + "+" + utente.getCognome());
            utente.setPassword(passwordEncoder.encode("pwduser"));
            utente.setRoles(Set.of(Role.ROLE_USER));
            utenteRepository.save(utente);

        }
        Utente admin = new Utente();
        admin.setNome(faker.name().firstName());
        admin.setCognome(faker.name().lastName());
        admin.setEmail("admin" + "@gmail.com");
        admin.setUsername("admin");
        admin.setAvatar("https://ui-avatars.com/api/?name=" + admin.getNome() + "+" + admin.getCognome());
        admin.setPassword(passwordEncoder.encode("pwdadmin"));
        admin.setRoles(Set.of(Role.ROLE_ADMIN));
        utenteRepository.save(admin);
    }
}
