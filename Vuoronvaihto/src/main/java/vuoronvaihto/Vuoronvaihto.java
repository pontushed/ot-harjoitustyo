/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto;

import javafx.application.Application;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot-sovelluskehyksen main-looppi.
 * @author pontus
 */
@SpringBootApplication
public class Vuoronvaihto {
    
    /**
     * Main.class main() kutsuu tätä metodia.
     * @param args Argumentit
     */
    public static void main(String[] args) {
        Application.launch(vuoronvaihto.ui.VuoronvaihtoGUI.class, args);        
    }
    
    /**
     * Tätä tarvitaan FxWeaverin asettamiseen.
     * FxWeaver kytkee yhteen Spring Bootin ja JavaFX:n.
     * @param applicationContext Spring Boot-konteksti
     * @return SpringFxWeaver-objekti joka on kytketty Spring Bootin kontekstiin.
     */
    @Bean
    public FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext) {
        return new SpringFxWeaver(applicationContext);
    }
}