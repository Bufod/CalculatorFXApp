package code;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Calculator extends Application {

    public Button btClose;
    private double initialX, initialY;

    public AnchorPane contentPane;

    private void addDraggableNode(final Node node){
        initialX = 0;
        initialY = 0;

        node.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() != MouseButton.MIDDLE){
                    initialX = event.getSceneX();
                    initialY = event.getSceneY();
                }
            }
        });

        node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() != MouseButton.MIDDLE) {
                    node.getScene().getWindow().setX(event.getScreenX()-initialX);
                    node.getScene().getWindow().setY(event.getScreenY()-initialY);
                }
            }
        });
    }

    @FXML
    void initialize() throws IOException {
        addDraggableNode(contentPane);

        Transition.setRootPane(contentPane);
        Transition.integrateRoot("/layouts/activity_main.fxml", Calculator.class);

        btClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Transition.hideWindow((Node)event.getSource());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/layout_basic.fxml"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
