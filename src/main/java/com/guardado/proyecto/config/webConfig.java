package com.guardado.proyecto.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir}")
    private String uploadDir;

    // Mapea las URLs /uploads/archivo.jpg a la carpeta real en disco
    // (local: ./uploads, Railway: el Volume montado).
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String ubicacion = uploadDir.endsWith("/") ? uploadDir : uploadDir + "/";
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + ubicacion);
    }
}