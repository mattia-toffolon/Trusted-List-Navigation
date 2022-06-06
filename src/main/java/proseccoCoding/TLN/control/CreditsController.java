package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import proseccoCoding.TLN.App;

/**
 * Controller of the credits view.
 *It has the duty to show the user the credits view and let him go back to the homepage.
 */
public class CreditsController {

    /**
     * Switches scene to the "home" one
     * @throws IOException
     */
	@FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
}