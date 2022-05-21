package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import proseccoCoding.TLN.App;

public class ListServicesController {

	@FXML
	private ListView servicesList;
	
    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
}