import au.edu.uts.ap.javafx.*;
import javafx.application.Application;
import javafx.stage.Stage;

import model.Animals.*;
import model.Users.*;
import model.Application.*;

public class Prog2AdoptionCentreApp extends Application{
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Animals seedAnimals = new Animals().insertSeedData();
        Users seedUsers = new Users().insertSeedData(seedAnimals);
        ViewLoader.showStage(new AdoptionCentre(seedAnimals, seedUsers), "/view/LoginView.fxml", "Login", new Stage());
    }
}