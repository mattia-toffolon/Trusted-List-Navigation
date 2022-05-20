package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import proseccoCoding.TLN.App;

public class ListServicesController {

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
}