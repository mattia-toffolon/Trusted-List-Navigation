package proseccoCoding.TLN;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

	/**
	 * App scene
	 */
    private static Scene scene;

    @Override
    /**
     * Starts the App by initializing the App scene and setting it as stage scene
     * @param stage App stage
     * @throws IOException
     */
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("home"));
        stage.setScene(scene);
        stage.setTitle("Trusted List Navigation");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("eu_icon.png")));
        stage.show();
    }

    /**
     * Changes the scene to the given one
     * @param fxml
     * @throws IOException
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Uses the given string to determine and locate the corresponding .fxml file
     * @param fxml
     * @return Parent object used to load the wanted .fxml file
     * @throws IOException
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Starts the App by calling the launch method
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

}