/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.ui;

import java.util.Arrays;
import java.util.List;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vuoronvaihto.dao.ShiftRepository;
import vuoronvaihto.dao.UserObjectRepository;
import vuoronvaihto.domain.*;
import vuoronvaihto.service.DaoService;
/**
 *
 * @author pontus
 */
@Component
@FxmlView("shiftview.fxml")
public class ShiftViewController {
    
    private final FxWeaver fxWeaver;
    
    private Stage stage;
    
    @Autowired
    private ShiftRepository shiftRepository;
    
    @Autowired
    private UserObjectRepository workerRepository;
        
    @Autowired
    private DaoService daoService;
    
    @FXML
    private VBox dialog;
    
    @FXML
    private TableView<Shift> tableView;
    
    @FXML
    private Label statusText;
    
    @FXML
    private Button changeButton;
    @FXML VBox aside;
    
    private Shift selectedShift;
    
    public ShiftViewController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }
    
    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        UserObject worker = daoService.getCurrentUser();
        String handle = worker.getHandle();
        stage.setTitle("Vuoronvaihtosovellus [" + handle + "]");
        ObservableList<Shift> data = tableView.getItems();

        if (worker != null) {
            List<Shift> shifts = shiftRepository.findByWorker(worker);
            for (Shift s : shifts) {
                data.add(s);
            }   
        }
        tableView.setPlaceholder(new Label("Vuoroja ei ole."));
        tableView.setItems(data);
        
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        // Handler for selecting a row
        ObservableList<Shift> selectedItems = tableView.getSelectionModel().getSelectedItems();

        selectedItems.addListener(new ListChangeListener<Shift>() {
          @Override
          public void onChanged(Change<? extends Shift> change) {
            //System.out.println("Selection changed: " + change.getList());
            selectedShift = change.getList().get(0);
            statusText.setText(selectedShift.toString());
            changeButton.setDisable(false);
            aside.getChildren().clear();
          }
        });

    }
    
    public void show() {
        stage.show();
    }
    
    @FXML
    private void handlePrevButtonAction(ActionEvent event) {
        System.out.println("Prev button clicked");
    }
    
    @FXML
    private void handleNextButtonAction(ActionEvent event) {
        System.out.println("Next button clicked");
    }
    
    @FXML
    private void handleChangeButtonAction(ActionEvent event) {        
        System.out.println("Change button clicked");
        asideList();
    }
    
    private void asideList() {
        List<Shift> applicableShifts = daoService.getApplicableShifts(selectedShift);
        aside.getChildren().clear();
        if (applicableShifts.isEmpty()) {
            aside.getChildren().add(new Label("Ei vaihtoehtoisia vuoroja."));
        } else {      
            aside.getChildren().add(new Label("Vaihtoehdot vuorolle:"));
            boolean[] selected = new boolean[applicableShifts.size()];
            for (int i=0;i<applicableShifts.size();i++) {
                HBox hbox = new HBox();
                CheckBox cb = new CheckBox();
                final int x = i;
                cb.setOnAction(e -> {                
                    selected[x] = !selected[x];
                    statusText.setText("Checkbox " + x + "=" + selected[x]);
                    System.out.println(Arrays.toString(selected));
                });
                Shift s = applicableShifts.get(i);
                String shiftText = s.getWorker().toString() + ":" + s.getShiftCode();
                Label l = new Label(shiftText);
                hbox.getChildren().addAll(cb,l);
                aside.getChildren().add(hbox);
            }
        }
    }
        
    
    /**
     * Logout and return to login window
     * @param event Quit-button pressed
     */
    @FXML
    private void handleQuitButtonAction(ActionEvent event) {
        System.out.println("Quit button clicked");
        fxWeaver.loadController(LoginController.class).show();
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
