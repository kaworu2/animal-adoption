package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Animals.Animal;
import model.Animals.Cat;
import model.Animals.Dog;
import model.Animals.Rabbit;
import model.Application.AdoptionCentre;
import model.Exceptions.InvalidOperationException;

public class AddAnimalController extends Controller<AdoptionCentre>{
    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private ComboBox<String> typeCombo;
    @FXML private Button addbtn;
    @FXML
    private void initialize() {
        typeCombo.getSelectionModel().selectFirst();
        BooleanBinding allEmpty = 
            nameField.textProperty().isEmpty()
            .and(ageField.textProperty().isEmpty());

        addbtn.disableProperty().bind(allEmpty);
    }
    public AdoptionCentre getCentre() {
        return model;
    }   

    @FXML
    private void handleAdd(ActionEvent event) {
        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String type = typeCombo.getValue();
        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            openErrorView(new InvalidOperationException("Age must be an Integer"));
            return;
        }
        Animal a;
        switch (type) {
            case "Cat": a = new Cat(name, age); break;
            case "Dog": a = new Dog(name, age); break;
            case "Rabbit": a = new Rabbit(name, age); break;
            default: a = new Animal(name, age); break;
        }
        try {
            boolean exists = getCentre().getAnimals().getAnimals().stream().anyMatch(x -> x.getName().equalsIgnoreCase(name));
            if (exists) {
                throw new InvalidOperationException(
                    name + " already exists in adoption center"
                );
            }
            getCentre().getAnimals().getAnimals().add(a);
            Stage st = (Stage) nameField.getScene().getWindow();
            st.close();
    
        } catch (InvalidOperationException ex) {
            
            openErrorView(ex);
        }
    }
        
            @FXML
    private void handleCancel(ActionEvent event) {
        Stage st = (Stage) nameField.getScene().getWindow();
        st.close();
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

