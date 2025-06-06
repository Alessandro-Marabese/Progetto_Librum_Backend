package it.epicode.Progetto_Librum_Backend.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}
