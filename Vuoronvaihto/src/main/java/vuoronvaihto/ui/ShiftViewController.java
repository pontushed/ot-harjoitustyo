/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.ui;

import java.util.List;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
import vuoronvaihto.dao.*;
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
    private ProposalRepository proposalRepository;
        
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
    
    @FXML
    private VBox aside;
    
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
        asideList();
    }
    
    /**
     * Show the list of applicable shift change opportunities.
     */
    private void asideList() {
        List<Shift> applicableShifts = daoService.getApplicableShifts(selectedShift);        
        aside.getChildren().clear();
        if (applicableShifts.isEmpty()) {
            aside.getChildren().add(new Label("Ei vaihtoehtoisia vuoroja."));
        } else {      
            aside.getChildren().add(new Label("Vaihtoehdot vuorolle:"));            
            VBox shiftList = new VBox();
            aside.getChildren().add(shiftList);
            for (Shift s : applicableShifts) {
                showApplicableShift(shiftList, s);
            }                        
        }
        
        List<Proposal> proposals = daoService.getProposals(selectedShift);
        if (!proposals.isEmpty()) {
            aside.getChildren().add(new Label("Vuoronvaihtoehdotuksesi:"));
            VBox vbox = new VBox();
            aside.getChildren().add(vbox);
            for (Proposal p : proposals) {
                vbox.getChildren().add(new Label(p.toString()));
            }            
        }
        
    }
    
    /**
     * Show success alert.
     */
    private void proposalSuccess() {
        Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle("Ilmoitus");
        a.setHeaderText("Onnistuminen");
        a.setContentText("Ehdotukset käsiteltiin onnistuneesti.");
        a.setOnCloseRequest(eh -> {
            aside.getChildren().clear();
        });
        a.show();
    }
    
    /** 
     * Show error alert.
     */
    private void proposalError() {
        Alert a = new Alert(AlertType.ERROR);
        a.setTitle("Sovellusvirhe");
        a.setHeaderText("Virhe");
        a.setContentText("Ehdotusten käsittelyssä tapahtui virhe.");
        a.setOnCloseRequest(eh -> {
            aside.getChildren().clear();
        });
        a.show();
    }
    
    /**
     * Helper function to check if at least one element is true.
     */
    private boolean isOneTrue(boolean[] r) {
        for (int i=0;i<r.length;i++) {
            if (r[i]) return true;
        }
        return false;
    }
    
    /**
     * Logout and return to login window
     * @param event Quit-button pressed
     */
    @FXML
    private void handleQuitButtonAction(ActionEvent event) {
        fxWeaver.loadController(LoginController.class).show();
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Temporary development function to clear proposals.
     */
    @FXML
    private void handleClearProposalsButtonAction(ActionEvent event) {
        proposalRepository.deleteAll();
    }

    /**
     * Create a HBox with a Button and information about a shift.
     * @param v The VBox where the HBox will be added
     * @param s Shift
     */
    private void showApplicableShift(VBox v, Shift s) {
        HBox hbox = new HBox();
        Button proposeButton = new Button("Pyydä");        
        String shiftText = s.getWorker().toString() + ":" + s.getShiftCode();
        Label l = new Label(shiftText);
        hbox.getChildren().addAll(proposeButton,l);
        v.getChildren().add(hbox);
        
        proposeButton.setOnAction((ActionEvent e) -> {
            if (proposeButton.getText().equals("Peru pyyntö")) {
                daoService.deleteProposal(s, daoService.getCurrentUser());
                daoService.deleteProposal(selectedShift, s.getWorker());
                proposeButton.setText("Pyydä");
            } else {
                daoService.addProposal(selectedShift, s);
                proposeButton.setText("Peru pyyntö");
            }            
        });
    }
}
