package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import au.edu.uts.ap.javafx.Controller;
public class ErrorController extends Controller<Exception> {
    @FXML private Label lginlabel;
    @FXML private Label manglabel;
    @FXML
    public void initialize() {
        lginlabel.setText(model.getMessage());
        manglabel.setText(model.getClass().getSimpleName());
    }

    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}