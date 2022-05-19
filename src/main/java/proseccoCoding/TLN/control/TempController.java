package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import proseccoCoding.TLN.App;

public class TempController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}