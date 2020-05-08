/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.ui;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
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
    
    private LocalDate startDate, endDate;
    
    public ShiftViewController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
        this.startDate = LocalDate.now();
        this.endDate = this.startDate.plusDays(12);
    }
    
    @FXML
    public void initialize() {
        this.stage = new Stage();
        Scene scene = new Scene(dialog);
        scene.getStylesheets().add("styles/stylesheet.css");
        stage.setScene(scene);        
        stage.setTitle("Vuoronvaihtosovellus [" + daoService.getCurrentUser().getHandle() + "]");
        
        generateSchedule();
        showProposals();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        // Handler for selecting a row
        ObservableList<Shift> selectedItems = tableView.getSelectionModel().getSelectedItems();
        selectedItems.addListener(new ListChangeListener<Shift>() {
          @Override
          public void onChanged(Change<? extends Shift> change) {            
            if (change.getList().isEmpty()) {
                selectedShift = null;
                changeButton.setDisable(true);
            } else {
                selectedShift = change.getList().get(0);
                statusText.setText(selectedShift.toString());
                changeButton.setDisable(false);
            }            
            aside.getChildren().clear();
            showProposals();
          }
        });

    }
    
    public void show() {
        stage.show();
    }
    
    public void generateSchedule() {
        UserObject worker = daoService.getCurrentUser();
        ObservableList<Shift> data = tableView.getItems();
        data.clear();
        if (worker != null) {
            LocalDate d = startDate;
            HashMap<LocalDate,Shift> schedule = new HashMap<>();
            List<Shift> shifts = daoService.getSchedule(startDate, endDate, worker);
            shifts.forEach((s) -> {
                schedule.put(s.getDateOfShift(),s);
            });
            while(d.isBefore(endDate)) {               
               if (schedule.containsKey(d)) {
                   data.add(schedule.get(d));
               } else {
                   data.add(new Shift(new Shiftcode("Vapaa", "12:00", 0), d.toString(), worker));
               }
               d = d.plusDays(1);
            }
        }
        tableView.setPlaceholder(new Label("Vuoroja ei ole."));
        tableView.setItems(data);
    }
    
    @FXML
    private void handlePrevButtonAction(ActionEvent event) {        
        startDate = startDate.minusDays(3);
        endDate = endDate.minusDays(3);
        statusText.setText("Date range: " + startDate.toString() + ":" + endDate.toString());
        generateSchedule();
    }
    
    @FXML
    private void handleNextButtonAction(ActionEvent event) {
        startDate = startDate.plusDays(3);
        endDate = endDate.plusDays(3);
        statusText.setText("Date range: " + startDate.toString() + ":" + endDate.toString());
        generateSchedule();
    }
    
    @FXML
    private void handleChangeButtonAction(ActionEvent event) {
        if (selectedShift.getShiftCode().toString().equals("Vapaa")) {
            System.out.println("Vapaapäivän vuoro, tee jotain muuta");
        } else {
            asideList();            
        }
    }
    
    /**
     * Show the list of applicable shift change opportunities.
     */
    private void asideList() {
        List<Shift> applicableShifts = daoService.getApplicableShifts(selectedShift);
        //List<Proposal> proposals = daoService.getProposals(selectedShift);
        HashSet<UserObject> proposalSet = new HashSet();
        //proposals.forEach(p -> proposalSet.add(p.getShift().getWorker()));
        aside.getChildren().clear();
        if (applicableShifts.isEmpty()) {
            aside.getChildren().add(new Label("Ei vaihtoehtoisia vuoroja."));
        } else {      
            aside.getChildren().add(new Label("Vaihtoehdot vuorolle:"));            
            VBox shiftList = new VBox();
            aside.getChildren().add(shiftList);
            applicableShifts.forEach((s) -> {
                int proposed = 0;                
                //Proposal p = daoService.getInboundProposals(daoService.getCurrentUser(), s);
                String p = null;
                if (p != null) {
                    proposed = -1;
                } else if (proposalSet.contains(s.getWorker())) {
                    proposed = 1;
                }
                addApplicableShift(shiftList, s, proposed);
            });                        
        }                
    }
    
    private void showProposals() {
        aside.getChildren().clear();
        UserObject u = daoService.getCurrentUser();
        HashMap<Shift,List<Shift>> outboundProposals = daoService.getOutboundProposals(u);
        HashMap<Shift,List<Shift>> inboundProposals = daoService.getInboundProposals(u);
        System.out.println(outboundProposals);
        System.out.println(inboundProposals);
        if (outboundProposals.isEmpty() && inboundProposals.isEmpty()) {
            aside.getChildren().add(new Label("Ei vuoronvaihtoehdotuksia."));
        }
        if (!outboundProposals.isEmpty()) {
            aside.getChildren().add(new Label("Tekemäsi ehdotukset:"));        
            outboundProposals.keySet().forEach(k -> {
                showOutboundProposalEntry(k,outboundProposals.get(k));
            });
        }
        if (!inboundProposals.isEmpty()) {
            aside.getChildren().add(new Label("Ehdotukset sinulle:"));        
            inboundProposals.keySet().forEach(k -> {
                showInboundProposalEntry(k,inboundProposals.get(k));
            });
        }
    }
    
    /**
     * Show success alert.
     */
    private void proposalSuccess() {
        Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle("Ilmoitus");
        a.setHeaderText("Onnistuminen");
        a.setContentText("Vuoro vaihdettiin onnistuneesti.");
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
        Stage thisStage  = (Stage) source.getScene().getWindow();
        thisStage.close();
    }
    
    
    /**
     * Create a HBox with a Button and information about a shift.
     * @param v The VBox where the HBox will be added
     * @param s Shift
     */
    private void addApplicableShift(VBox v, Shift s, int proposed) {
        HBox hbox = new HBox();
        Button proposeButton = new Button();
        switch (proposed) {
            case -1:
                proposeButton.setText("Hyväksy");
                break;
            case 1:
                proposeButton.setText("Peru pyyntö");
                break;
            default:
                proposeButton.setText("Pyydä");
                break;
        }
        String shiftText = s.getWorker().toString() + ":" + s.getShiftCode();
        Label l = new Label(shiftText);
        hbox.getChildren().addAll(proposeButton,l);
        v.getChildren().add(hbox);
        
        proposeButton.setOnAction((ActionEvent e) -> {
            switch (proposeButton.getText()) {
                case "Hyväksy":                    
                    //daoService.swap(s, selectedShift);
                    proposalSuccess();
                    generateSchedule();                    
                    break;
                case "Peru pyyntö":                    
                    daoService.deleteProposal(selectedShift, s);
                    proposeButton.setText("Pyydä");
                    break;
                default:
                    daoService.addProposal(selectedShift, s);
                    proposeButton.setText("Peru pyyntö");
                    break;
            }
        });
    }

    private void showInboundProposalEntry(Shift s, List<Shift> proposals) {
        UserObject u = daoService.getCurrentUser();
        proposals.forEach(p -> {
            HBox hbox = new HBox();
            Button proposeButton = new Button("Hyväksy");        
            String shiftText = s.getWorker().toString() + ":" + s.getShiftCode();
            Label l = new Label(shiftText);
            hbox.getChildren().addAll(proposeButton,l);
            aside.getChildren().add(hbox);
            proposeButton.setOnAction((ActionEvent e) -> {                         
                daoService.swap(s, p);
                proposalSuccess();
                generateSchedule();                            
            });
        });
    }
    
    private void showOutboundProposalEntry(Shift s, List<Shift> proposals) {
        UserObject u = daoService.getCurrentUser();
        proposals.forEach(p -> {
            HBox hbox = new HBox();
            Button proposeButton = new Button("Peru pyyntö");
            String shiftText = s.getWorker().toString() + ":" + s.getShiftCode();
            Label l = new Label(shiftText);
            hbox.getChildren().addAll(proposeButton,l);
            aside.getChildren().add(hbox);
            proposeButton.setOnAction((ActionEvent e) -> {                        
                daoService.deleteProposal(s, p);
                aside.getChildren().remove(hbox);
            });
        });
    }
}
