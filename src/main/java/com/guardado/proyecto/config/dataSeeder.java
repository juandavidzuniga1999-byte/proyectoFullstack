package com.guardado.proyecto.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import com.guardado.proyecto.model.propiedad;
import com.guardado.proyecto.repository.propiedadRepository;
 
@Configuration
public class dataSeeder {
 
    // Si la tabla esta vacia, mete un par de propiedades de ejemplo para
    // que puedas ver el apartado funcionando de inmediato.
    // Puedes borrar esta clase cuando ya tengas datos reales o un panel
    // para cargarlos.
    @Bean
    public CommandLineRunner sembrarDatos(propiedadRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                propiedad p1 = new propiedad();
                p1.setTitulo("Casa en Venta Fracc. Villas de Altamira");
                p1.setImagenUrl("images/casa1.jpg");
                p1.setTipoPropiedad("Casa");
                p1.setConstruccion(165);
                p1.setTerreno(89);
                p1.setCondicion("En Venta");
                p1.setUbicacion("Altamira");
                p1.setPrecio(1150000);
                repo.save(p1);
 
                propiedad p2 = new propiedad();
                p2.setTitulo("Casa en Venta Col. Tamaulipas, Altamira");
                p2.setImagenUrl("images/marmol.jpg");
                p2.setTipoPropiedad("Casa");
                p2.setConstruccion(83);
                p2.setTerreno(120);
                p2.setCondicion("Remates Hipotecarios");
                p2.setUbicacion("Altamira");
                p2.setPrecio(767880);
                repo.save(p2);
 
                propiedad p3 = new propiedad();
                p3.setTitulo("Casa en Venta Fracc. Santa Elena, Altamira");
                p3.setImagenUrl("images/casa2.jpg");
                p3.setTipoPropiedad("Casa");
                p3.setConstruccion(45);
                p3.setTerreno(119);
                p3.setCondicion("Proceso");
                p3.setUbicacion("Altamira");
                p3.setPrecio(750000);
                repo.save(p3);
            }
        };
    }
}

