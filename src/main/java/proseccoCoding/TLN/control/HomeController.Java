package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import proseccoCoding.TLN.App;

public class HomeController {

    @FXML
    private void switchToListCountries() throws IOException {
        App.setRoot("listCountries");
    }
    
}