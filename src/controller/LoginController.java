package controller;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.stage.Stage;
import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import model.Application.AdoptionCentre;
import model.Exceptions.UnauthorizedAccessException;
import model.Users.Customer;
import model.Users.Manager;

public class LoginController extends Controller<AdoptionCentre>{
    @FXML private ImageView logoView;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private TextField managerIdField;
    @FXML private Button loginButton;

    @FXML
    private void initialize() {
        BooleanBinding managerEmpty = managerIdField.textProperty().isEmpty();
        BooleanBinding usernameEmpty = usernameField.textProperty().isEmpty();
        BooleanBinding emailEmpty    = emailField.textProperty().isEmpty();

        BooleanBinding canLoginAsCustomer =
            usernameEmpty.not().and(emailEmpty.not());
        BooleanBinding canLogin =
            managerEmpty.not().or(canLoginAsCustomer);

        loginButton.disableProperty().bind(canLogin.not());

        BooleanBinding customerTyping =
            usernameField.textProperty().isNotEmpty()
            .or(emailField.textProperty().isNotEmpty());
        managerIdField.disableProperty().bind(customerTyping);

        BooleanBinding managerTyping =
            managerIdField.textProperty().isNotEmpty();
            usernameField.disableProperty().bind(managerTyping);
            emailField.disableProperty().bind(managerTyping);

        BooleanBinding allEmpty = 
            usernameField.textProperty().isEmpty()
            .and(emailField.textProperty().isEmpty())
            .and(managerIdField.textProperty().isEmpty());

        loginButton.disableProperty().bind(allEmpty);
    }
    public AdoptionCentre getCentre() {
        return model;
      }
    @FXML
    private void handleLogin(ActionEvent event) throws Exception {
        String username = usernameField.getText().trim();
        String email    = emailField.getText().trim();
        String mgrIdstr    = managerIdField.getText().trim();
        
        
        if (!mgrIdstr.isEmpty()) {
            
            try {
                Integer.parseInt(mgrIdstr);
            } catch (NumberFormatException e) {
                openErrorView(new UnauthorizedAccessException("ID must be an integer"));
                return;
            }
            try {
                Manager m = getCentre().getUsers().validateManager(mgrIdstr);
                ViewLoader.showStage(getCentre(), "/view/ManagerDashboard.fxml", "Manager Dashboard", new Stage());
                closeWindow(event);
            } catch (UnauthorizedAccessException uae) {
                openErrorView(uae);  
            }
            return;
        }
        try {
            AdoptionCentre centre = this.model;
            Customer c = getCentre().getUsers().validateCustomer(username, email);
            ViewLoader.showStage(getCentre(), "/view/CustomerDashboard.fxml", "Customer Dashboard", new Stage());
            closeWindow(event);
        } catch (UnauthorizedAccessException uae) {
            openErrorView(uae);  
        }
    }

    @FXML
    private void handleExit(ActionEvent event) {
        closeWindow(event);
    }

    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
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
   