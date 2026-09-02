package com.cuentas_bancarias.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
* Configuración principal de la arquitectura hexagonal.
* Define los componentes y beans de la aplicación.
*/
@Configuration
@ComponentScan(basePackages = {
        "com.cuentas_bancarias.application.service",
        "com.cuentas_bancarias.infrastructure.adapters",
        "com.cuentas_bancarias.infrastructure.config"
})
public class HexagonalConfig implements WebMvcConfigurer {

   @Override
   public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/**")
               .allowedOrigins("http://localhost:3000")
               .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
               .allowedHeaders("*")
               .allowCredentials(true);
   }
}
