package proseccoCoding.TLN.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Separator;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import proseccoCoding.TLN.App;
import proseccoCoding.TLN.model.TrustedListFacade;

/**
 * 
 * Controller of the selectStatus view
 *
 */
public class SelectStatusController {


	@FXML
	/**
	 * VBox object used to contain all the CheckBoxes used to let the user select his service statuses of interest
	 */
	private VBox serviceStatusesPane;
	/**
	 * CheckBox used to select/deselect all the service status CheckBoxes at the same time
	 */
	private CheckBox selectAll;
	/**
	 * Custom ChangeListener used to define the first part of the selectAll CheckBox behavior
	 */
	ChangeListener<Boolean> selectAllListener = new ChangeListener<Boolean>(){
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	    	selectAllChanged(newValue);
	    }
	};
	/**
	 * Custom ChangeListener used to define the second part of the selectAll CheckBox behavior
	 */
	ChangeListener<Boolean> checkBoxListener = new ChangeListener<Boolean>(){
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	    	checkBoxChanged(newValue);
	    }
	};
	
	@FXML
	/**
	 * Method called when SelectServiceStatusController is loaded.
	 * This method adds to serviceStatusesPane the selectAll CheckBox and a CheckBox for each service status 
	 * that is associated with at least one service which service type, country and provider was previously selected, all with their ChangeListener.
	 */
	private void initialize() {
		// selectAll CheckBox is created 
		selectAll = new CheckBox("Select All");
		serviceStatusesPane.setSpacing(5);
		serviceStatusesPane.setPadding(new Insets(5));
		serviceStatusesPane.getChildren().add(selectAll);
		serviceStatusesPane.getChildren().add(new Separator());		
		
		// a ChangeListener is added to the selectAll CheckBox properties 
		selectAll.selectedProperty().addListener(selectAllListener);
		selectAll.indeterminateProperty().addListener(selectAllListener);
		
		ArrayList<String> serviceStatuses = TrustedListFacade.getInstance().getQuery().getAvailableServiceStatus();
		Collections.sort(serviceStatuses);

		// a CheckBox is added with a ChangeListener for each provider which country has been selected
		for (String s : serviceStatuses) {
			CheckBox cb = new CheckBox(s);
			cb.selectedProperty().addListener(checkBoxListener);
			serviceStatusesPane.getChildren().add(cb);
		}
	}

	/**
	 * This private method is used to change every CheckBox status to the selectAll one
	 * @param value
	 */
	private void selectAllChanged(Boolean value) {
    	if(selectAll.isIndeterminate())
    		return;
    	// every CheckBox in the VBox is set to the same status as selectAll CheckBox
    	for(Node node : serviceStatusesPane.getChildren()) {
    		if(node.getClass().equals(CheckBox.class) && !node.equals(selectAll)) {
	    		CheckBox cb = (CheckBox)node;
	    		cb.selectedProperty().set(selectAll.isSelected());
    		}
    	}
    }
    
	/**
	 * This private method is used to manage the indeterminate status of the selectAll CheckBox
	 * If a service status CheckBox is set to false and the selectAll one was previously set to true, selectAll becomes indeterminate.
	 * @param newValue
	 */
    private void checkBoxChanged(Boolean newValue) {
    	if(newValue == false && selectAll.isSelected())
    		selectAll.setIndeterminate(true);
    }
	
    @FXML
    /**
     * Switches scene to the "selectServices" one
     * @throws IOException
     */
    private void switchToSelectServices() throws IOException {
        App.setRoot("selectServices");
    }
    
    @FXML
    /**
     * Switches scene to the "queryResults" one.
     * This method also tracks down the selected service statuses via checking the status of the CheckBoxes and sets selectedServiceStatus in TrustedListFacade's Query.
     * If no service status was selected, a warning alert is set to inform the user of his mistake.
     * @throws IOException
     */
    private void switchToQueryResults() throws IOException {
    	ArrayList<String> selectedStatuses = new ArrayList<String>();
    	for(Node node : serviceStatusesPane.getChildren()) {
    		if(node.getClass().equals(CheckBox.class) && !node.equals(selectAll)) {
	    		CheckBox cb = (CheckBox)node;
	    		if(cb.isSelected()==true) 
	    			selectedStatuses.add(cb.getText());
    		}
    	}
    	if(selectedStatuses.isEmpty()){
    		Alert a = new Alert(AlertType.WARNING, "User must select at least one service status.");
    		a.setHeaderText("Invalid parameters selection");
			a.setTitle("Warning");
			((Stage)a.getDialogPane().getScene().getWindow()).getIcons().add(new Image(getClass().getResourceAsStream("eu_icon.png")));    		
			a.showAndWait();
    		return;
    	}
    	TrustedListFacade.getInstance().getQuery().setSelectedServiceStatus(selectedStatuses);
        App.setRoot("queryResults");
    }
}