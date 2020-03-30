/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto;

/**
 *
 * @author pontus
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class VuoronvaihtoUI extends Application {
    @Override
    public void start(Stage ikkuna) throws Exception {
 
        Label ohjeteksti = new Label("Kirjoita tunnuksesi ja aloita.");
        TextField nimikentta = new TextField();
        Button aloitusnappi = new Button("Aloita");
 
        GridPane asettelu = new GridPane();
        asettelu.setPrefSize(300, 180);
        asettelu.setAlignment(Pos.CENTER);
 
        asettelu.add(ohjeteksti, 0, 0);
        asettelu.add(nimikentta, 0, 1);
        asettelu.add(aloitusnappi, 0, 2);
 
        asettelu.setVgap(10);
        asettelu.setHgap(10);
        asettelu.setPadding(new Insets(20, 20, 20, 20));
        Scene salasanaNakyma = new Scene(asettelu);
 
        // Aloitusruutu
        Label tervetuloaTeksti = new Label("");
 
        StackPane tervetuloaAsettelu = new StackPane();
        tervetuloaAsettelu.setPrefSize(300, 180);
        tervetuloaAsettelu.getChildren().add(tervetuloaTeksti);
        tervetuloaAsettelu.setAlignment(Pos.CENTER);
 
        Scene tervetuloaNakyma = new Scene(tervetuloaAsettelu);
 
        aloitusnappi.setOnAction((event) -> {
            tervetuloaTeksti.setText("Tervetuloa " + nimikentta.getText() + "!");
            ikkuna.setScene(tervetuloaNakyma);
        });
 
        ikkuna.setScene(salasanaNakyma);
        ikkuna.show();
    }

    public static void main(String[] args) {
        launch(VuoronvaihtoUI.class);
    }
}
