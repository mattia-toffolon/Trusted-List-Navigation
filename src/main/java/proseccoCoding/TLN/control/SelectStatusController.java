package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import proseccoCoding.TLN.App;

public class SelectStatusController {

    @FXML
    private void switchToSelectServices() throws IOException {
        App.setRoot("selectServices");
    }
    
    @FXML
    private void switchToQueryResults() throws IOException {
        App.setRoot("queryResults");
    }
}