/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import vuoronvaihto.service.UtilityService;

/**
 *
 * @author pontus
 */
@Component
public class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;
    
    @Autowired
    private UtilityService ts;

    @Autowired
    public PrimaryStageInitializer(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.stage;
        Scene scene = new Scene(fxWeaver.loadView(LoginController.class), 300, 180);
        scene.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Vuoronvaihtosovellus");
        ts.initializeDatabase();
        stage.show();
    }
}