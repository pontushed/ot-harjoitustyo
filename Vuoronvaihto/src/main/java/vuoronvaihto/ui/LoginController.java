/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vuoronvaihto.service.DaoService;

/**
 *
 * @author pontus
 */
@Component
@FxmlView("login.fxml")
public class LoginController {
    private Stage stage;
    private final FxWeaver fxWeaver;
     
    @Autowired
    private DaoService daoService;
    
    @FXML
    private TextField loginText;
    
    @FXML
    private Label loginMessage;
    
    @FXML
    private GridPane dialog;
    
    public LoginController( FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }
        
    @FXML
    public void loginButtonPressed(ActionEvent e) {        
        if (daoService.login(loginText.getText())) {            
            fxWeaver.loadController(ShiftViewController.class).show();
            Node  source = (Node)  e.getSource(); 
            Stage stage  = (Stage) source.getScene().getWindow();
            stage.close();
        } else {
            loginMessage.setText("Käyttäjää '" + loginText.getText() + "' ei löydy. Kokeile uudelleen.");
            loginMessage.setTextFill(Color.RED);
        }
    }
    
    public void show() {
        daoService.logout();
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.setTitle("Vuoronvaihtosovellus");
        stage.show();
    }
    
}
