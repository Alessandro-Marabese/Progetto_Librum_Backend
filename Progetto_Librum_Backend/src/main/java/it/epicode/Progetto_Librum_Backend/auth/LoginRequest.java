package it.epicode.Progetto_Librum_Backend.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
