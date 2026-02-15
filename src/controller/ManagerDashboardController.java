package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Animals.Animal;
import model.Application.AdoptionCentre;

public class ManagerDashboardController extends Controller<AdoptionCentre>{
  
    @FXML private ImageView managerView;
    @FXML private ToggleButton allbtn;
    @FXML private ToggleButton catbtn;
    @FXML private ToggleButton dogbtn;
    @FXML private ToggleButton rabbitbtn;
    @FXML private TableView<Animal> animalTable;
    @FXML private TableColumn<Animal, String> nameCol;
    @FXML private TableColumn<Animal, String> typeCol;
    @FXML private TableColumn<Animal, Integer> ageCol;
    @FXML private TableColumn<Animal, String> statusCol;
    @FXML private Button listbtn;
    @FXML private Button addbtn;
    @FXML private Button removebtn;
    @FXML private Button closebtn;

    private final ObservableList<Animal> masterList = FXCollections.observableArrayList();

      
    public AdoptionCentre getCentre() {
        return model;
    }

    @FXML
    private void initialize() {
        
        nameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
        typeCol.setCellValueFactory(cell -> cell.getValue().typeProperty());
        ageCol.setCellValueFactory(cell -> cell.getValue().ageProperty().asObject());
        statusCol.setCellValueFactory(cell -> cell.getValue().isAdoptedProperty());

        ToggleGroup group = new ToggleGroup();
        allbtn.setToggleGroup(group);
        catbtn.setToggleGroup(group);
        dogbtn.setToggleGroup(group);
        rabbitbtn.setToggleGroup(group);
        allbtn.setSelected(true);

        masterList.addAll(getCentre().getAnimals().getAnimals());
        animalTable.setItems(masterList);
        
        
        removebtn.disableProperty().bind(
            animalTable.getSelectionModel().selectedItemProperty().isNull()
        );
    }

   
    @FXML
    private void handleFilter(ActionEvent event) {
        String text = ((ToggleButton) event.getSource()).getText();
        masterList.clear();
        for (Animal a : getCentre().getAnimals().getAnimals()) {
            if (text.equals("All") || a.getClass().getSimpleName().equalsIgnoreCase(text)) {
                masterList.add(a);
            }
        }
        animalTable.setItems(masterList);
    }

  
    @FXML
    private void handleUserList(ActionEvent event) throws Exception {
        ViewLoader.showStage(
            getCentre().getUsers(), "/view/UserListView.fxml", "User List", new Stage()
        );
    }

   
    @FXML
    private void handleAdd(ActionEvent event) throws Exception {
        ViewLoader.showStage(
            getCentre(), "/view/AddAnimalView.fxml", "Add Animal", new Stage()
        );
    }

   
    @FXML
    private void handleRemove(ActionEvent event) {
        Animal a = animalTable.getSelectionModel().getSelectedItem();
        if (a == null) return;
        if (a.isAdopted()) {
            model.Exceptions.InvalidOperationException ex = new model.Exceptions.InvalidOperationException(a.getName() + " has been adopted");
            openErrorView(ex);
            return;
        }
        getCentre().getAnimals().remove(a);
        masterList.remove(a);
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

    
    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }
}

