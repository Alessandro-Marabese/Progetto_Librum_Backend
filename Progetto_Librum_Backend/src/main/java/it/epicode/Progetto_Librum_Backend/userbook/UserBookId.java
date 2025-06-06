package it.epicode.Progetto_Librum_Backend.userbook;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserBookId implements Serializable {
    private Long utenteId;
    private String libroId;
}
