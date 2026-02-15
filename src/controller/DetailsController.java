package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Application.AdoptionCentre;
import model.Animals.Animal;
import model.Users.Customer;

public class DetailsController extends Controller<AdoptionCentre> {
    @FXML private Label lblName;
    @FXML private TableView<Animal> adoptedTable;
    @FXML private TableColumn<Animal, String> colAdoptedDisplay;
    @FXML private Button closebtn;

    public AdoptionCentre getCentre() {
        return model;
    }
    @FXML
    private void initialize() {
        
        Customer current = (Customer) getCentre().getLoggedInUser();

        lblName.setText(current.getName());
        ObservableList<Animal> adopted = current.adoptedAnimals().getAnimals();

        colAdoptedDisplay.setCellValueFactory(cell ->
            new SimpleStringProperty(cell.getValue().toString())
        );

        adoptedTable.setItems(adopted);
    }

    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }
}