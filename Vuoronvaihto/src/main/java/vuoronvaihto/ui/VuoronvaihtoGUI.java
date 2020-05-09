/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.ui;



import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * GUI for the application.
 * @author pontus
 */
public class VuoronvaihtoGUI extends Application {
    
    private ConfigurableApplicationContext applicationContext;
            
    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.applicationContext = new SpringApplicationBuilder()
                .sources(vuoronvaihto.Vuoronvaihto.class)
                .run(args);    
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        applicationContext.publishEvent(new StageReadyEvent(stage)); //(2)      
    }
    
    
}
