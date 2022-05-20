package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import proseccoCoding.TLN.App;

public class SelectProvidersController {

    @FXML
    private void switchToSelectCountries() throws IOException {
        App.setRoot("selectCountries");
    }
    
    @FXML
    private void switchToSelectServices() throws IOException {
        App.setRoot("selectServices");
    }
}