package proseccoCoding.Progetto1.control;

import java.io.IOException;
import javafx.fxml.FXML;
import proseccoCoding.Progetto1.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
}