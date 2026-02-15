package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Users.Customer;
import model.Users.Manager;
import model.Users.User;
import model.Users.Users;

public class UserListController extends Controller<Users>{
    @FXML ListView<String> userListView;
    @FXML Button closebtn;

    @FXML
    private void initialize() {
        
        ObservableList<String> items = FXCollections.observableArrayList();

       
        for (User u : model.getUsers()) {
            if (u instanceof Manager) {
                Manager m = (Manager) u;
                items.add(m.getName() + " (Manager)");
            }
        }
        for (User u : model.getUsers()) {
            if (u instanceof Customer) {
                Customer c = (Customer) u;
                items.add(c.getName() + " (" + c.getEmail() + ")");
            }
        }

        userListView.setItems(items);
    }

    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }
}
