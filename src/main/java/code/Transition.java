package code;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class Transition {

    private static Pane rootPane;

    public static void setRootPane(Pane rootPane) {
        Transition.rootPane = rootPane;
    }

    public static void clearPane(Pane pane){
        pane.getChildren().clear();
    }

    public static <T> T integrate(Pane pane, URL pathToFxml) throws IOException {
        clearPane(pane);
        FXMLLoader loader = new FXMLLoader(pathToFxml);
        Parent layout = loader.load();
        AnchorPane.setBottomAnchor(layout, 0.0);
        AnchorPane.setTopAnchor(layout, 0.0);
        AnchorPane.setLeftAnchor(layout, 0.0);
        AnchorPane.setRightAnchor(layout, 0.0);
        pane.getChildren().add(layout);
        return loader.getController();
    }

    public static <T> T integrateRoot(String pathToFxml, Class classController) throws IOException {
        clearPane(rootPane);
        return integrate(rootPane, classController.getResource(pathToFxml));
    }

    public static void hideWindow(Node node){
        node.getScene().getWindow().hide();
    }
}
