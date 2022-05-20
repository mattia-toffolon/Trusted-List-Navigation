package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import proseccoCoding.TLN.App;

public class HomeController {

    @FXML
    private void switchToListCountries() throws IOException {
        App.setRoot("listCountries");
    }
    
    @FXML
    private void switchToListServices() throws IOException {
        App.setRoot("listServices");
    }
    
    @FXML
    private void switchToSelectCountries() throws IOException {
        App.setRoot("selectCountries");
    }
    
    @FXML
    private void switchToCredits() throws IOException {
        App.setRoot("credits");
    }
}