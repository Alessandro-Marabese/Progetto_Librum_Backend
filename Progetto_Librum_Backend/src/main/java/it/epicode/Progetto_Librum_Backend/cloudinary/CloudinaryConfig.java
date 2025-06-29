package it.epicode.Progetto_Librum_Backend.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Value("${cloudinary.cloud_name}")
    private String cloudName;
    @Value("${cloudinary.api_key}")
    private String apiKey;
    @Value("${cloudinary.api_secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinari() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName); // Sostituisci con il tuo nome cloud
        config.put("api_key", apiKey); // Sostituisci con la tua API key
        config.put("api_secret",apiSecret); // Sostituisci con la tua API secret
        return new Cloudinary(config);
    }
}