package code.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import code.OperHistory;
import code.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ActivityHistory {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btBack;

    @FXML
    private VBox container;

    @FXML
    private Button btClear;

    void listInvalidate(){
        container.getChildren().clear();
        ArrayList<String> list = OperHistory.getInstance().getHistoryList();
        for(int i = list.size()-1; i >= 0; i--){
            Text text = new Text(list.get(i));
            text.setId("text");
            container.getChildren().add(text);
        }
    }

    @FXML
    void btClearAction(ActionEvent event) {
        listInvalidate();
    }

    @FXML
    void btBackAction(ActionEvent event) throws IOException {
        Transition.integrateRoot("/layouts/activity_main.fxml", this.getClass());
    }

    @FXML
    void initialize() {
        listInvalidate();
    }
}
