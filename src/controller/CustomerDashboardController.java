package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Animals.Animal;
import model.Application.AdoptionCentre;
import model.Users.Customer;
import model.Exceptions.InvalidOperationException;

public class CustomerDashboardController extends Controller<AdoptionCentre> {
    @FXML private Label lblWelcomeName;
    @FXML private TableView<Animal> petsTable;
    @FXML private TableColumn<Animal, String> colPetDisplay;
    @FXML private Button btnAdopt;
    @FXML private Button detailbtn;
    @FXML private Button closebtn;

    private ObservableList<Animal> localAnimals;
    
    public AdoptionCentre getCentre() {
        return model;
    }

    @FXML
    private void initialize() {
        colPetDisplay.setCellValueFactory(cell -> {
            Animal a = cell.getValue();
            String display = a.getName() + " (Age: " + a.ageProperty().get() + ")";
            return new SimpleStringProperty(display);
        });

       
        btnAdopt.disableProperty().bind(
            petsTable.getSelectionModel().selectedItemProperty().isNull()
        );
        Customer current = (Customer) getCentre().getLoggedInUser();
        lblWelcomeName.setText("Welcome " + current.getFirstName());

        
        localAnimals = FXCollections.observableArrayList();
        for (Animal a : getCentre().getAnimals().getAnimals()) {
            if (!a.isAdopted()) {
                localAnimals.add(a);
            }
        }
        petsTable.setItems(localAnimals);
    }

    @FXML
    private void handleAdopt() {
        Animal selected = petsTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        Customer current = (Customer) getCentre().getLoggedInUser();
        if (!current.canAdopt(selected)) {
            openErrorView(new InvalidOperationException(
                "Cannot adopt " + selected.getName()
                + ", adoption limit for " + selected.typeProperty().get() + "s reached"
            ));
            return;
        }
        selected.adopt();
        current.adoptedAnimals().add(selected);
        getCentre().getAnimals().getAnimals().remove(selected);
        localAnimals.remove(selected);
    }

   
    @FXML
    private void handleMyDetails(ActionEvent event) throws Exception {
        ViewLoader.showStage(
            getCentre(),
            "/view/DetailsView.fxml",
            "My Details",
            new Stage()
        );
    }

    @FXML
    private void handleLogout() {
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }

    private void openErrorView(Exception ex) {
        try {
            ViewLoader.showStage(
                ex,
                "/view/ErrorView.fxml",
                "Error",
                new Stage()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
