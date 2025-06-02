package it.epicode.Progetto_Librum_Backend.utenti;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.Progetto_Librum_Backend.auth.Role;
import it.epicode.Progetto_Librum_Backend.reviews.Review;
import it.epicode.Progetto_Librum_Backend.reviews.commenti.Commento;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "utenti")
public class Utente implements UserDetails {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String username;
    private String avatar;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    @Column(nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "utente")
    private List<Review> reviews;

    @OneToMany(mappedBy = "utente")
    private List<Commento> commenti;

    @ManyToMany
    @JoinTable(
            name = "utenti_amici",
            joinColumns = @JoinColumn(name = "utente_id"),
            inverseJoinColumns = @JoinColumn(name = "amico_id")
    )
    private Set<Utente> amici = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> (GrantedAuthority) role::name)
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    private  boolean accountNonExpired=true;
    private  boolean accountNonLocked=true;
    private  boolean credentialsNonExpired=true;
    private  boolean enabled=true;
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
