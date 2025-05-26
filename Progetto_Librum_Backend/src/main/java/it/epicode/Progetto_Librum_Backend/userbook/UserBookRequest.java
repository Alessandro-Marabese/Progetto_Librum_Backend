package it.epicode.Progetto_Librum_Backend.userbook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookRequest {

    private Long userId;
    private String bookId;
    private StatoLettura statoLettura;

}
