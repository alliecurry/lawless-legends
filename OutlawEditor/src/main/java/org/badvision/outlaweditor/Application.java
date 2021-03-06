package org.badvision.outlaweditor;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import static org.badvision.outlaweditor.UIAction.*;
import org.badvision.outlaweditor.data.xml.GameData;

/**
 *
 * @author brobert
 */
public class Application extends javafx.application.Application {

    public static GameData gameData = new GameData();
    public static Platform currentPlatform = Platform.AppleII;
    static Application instance;
    public Stage primaryStage;
    ApplicationUIControllerImpl controller;
    
    public static Stage getPrimaryStage() {
        return instance.primaryStage;
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        this.primaryStage = primaryStage;
        javafx.application.Platform.setImplicitExit(true);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ApplicationUI.fxml"));
        fxmlLoader.setResources(null);
        try {
            AnchorPane node = (AnchorPane) fxmlLoader.load();
            controller = fxmlLoader.getController();
            Scene s = new Scene(node);
            primaryStage.setScene(s);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent t) {
                t.consume();
                if (quit()) {
                    javafx.application.Platform.exit();
//                    System.exit(0);
                }
                javafx.application.Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        getPrimaryStage().show();
                    }
                });
            }
        });
        primaryStage.show();
    }
    Canvas tilePreview;

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}