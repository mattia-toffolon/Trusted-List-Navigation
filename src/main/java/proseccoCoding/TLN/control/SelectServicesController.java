package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import proseccoCoding.TLN.App;

public class SelectServicesController {

    @FXML
    private void switchToSelectStatus() throws IOException {
        App.setRoot("selectStatus");
    }
    
    @FXML
    private void switchToSelectProviders() throws IOException {
        App.setRoot("selectProviders");
    }
}