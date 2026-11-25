package com.restaurante.pedidos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Configuracion {

    @Bean
    public PasswordEncoder cifradorContrasenas() {
        return new BCryptPasswordEncoder();
    }
}
