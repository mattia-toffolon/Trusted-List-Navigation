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
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import proseccoCoding.TLN.App;
import proseccoCoding.TLN.model.TrustedListFacade;
import proseccoCoding.TLN.model.ServiceType;

/**
 * 
 * Controller of the "selectServices" section
 *
 */
public class SelectServicesController {
	
	@FXML
	/**
	 * VBox object used to contain all the CheckBoxes used to let the user select his service types of interest
	 */
	private VBox serviceTypesPane;
	/**
	 * CheckBox used to select/deselect all the service types CheckBoxes at the same time
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
	 * Method called when SelectServicesController is loaded.
	 * This method adds to serviceTypesPane the selectAll CheckBox and a CheckBox for each service type 
	 * which country and provider was previously selected, all with their ChangeListener.
	 */
	private void initialize() {
		// selectAll CheckBox is created 
		selectAll = new CheckBox("Select All");
		serviceTypesPane.setSpacing(5);
		serviceTypesPane.setPadding(new Insets(5));
		serviceTypesPane.getChildren().add(selectAll);
		Separator sep = new Separator();
		sep.setMinWidth(315);
		serviceTypesPane.getChildren().add(sep);		
		
		// a ChangeListener is added to the selectAll CheckBox properties 
		selectAll.selectedProperty().addListener(selectAllListener);
		selectAll.indeterminateProperty().addListener(selectAllListener);
		
		ArrayList<String> serviceTypeNames = new ArrayList<String>();
		for (ServiceType st : TrustedListFacade.getInstance().getQuery().getAvailableServiceTypes()) 
			serviceTypeNames.add(st.getCode() +" \n﹂ "+ st.getName());
		
		Collections.sort(serviceTypeNames);

		// a CheckBox is added with a ChangeListener for each provider which country has been selected
		for (String s : serviceTypeNames) {
			CheckBox cb = new CheckBox(s);
			cb.selectedProperty().addListener(checkBoxListener);
			serviceTypesPane.getChildren().add(cb);
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
    	for(Node node : serviceTypesPane.getChildren()) {
    		if(node.getClass().equals(CheckBox.class) && !node.equals(selectAll)) {
	    		CheckBox cb = (CheckBox)node;
	    		cb.selectedProperty().set(selectAll.isSelected());
    		}
    	}
    }
    
	/**
	 * This private method is used to manage the indeterminate status of the selectAll CheckBox
	 * @param newValue
	 */
    private void checkBoxChanged(Boolean newValue) {
    	if(newValue == false && selectAll.isSelected())
    		selectAll.setIndeterminate(true);
    }

    @FXML
    /**
     * Switches scene to the "selectStatus" one.
     * This method also tracks down the selected service types via checking the status of the CheckBoxs and sets selectedServicesByType in TrustedListFacade's Query.
     * If no service type was selected, a warning alert is set to inform the user of his mistake.
     * @throws IOException
     */
    private void switchToSelectStatus() throws IOException {
    	ArrayList<String> selectedServiceTypesCodes = new ArrayList<String>();
    	for(Node node : serviceTypesPane.getChildren()) {
    		if(node.getClass().equals(CheckBox.class) && !node.equals(selectAll)) {
	    		CheckBox cb = (CheckBox)node;
	    		if(cb.isSelected()==true) {
	    			String code = new String();
	    			for(int i=0; i<cb.getText().length(); i++) {
	    				if(cb.getText().charAt(i)!=' ')
	    					code += cb.getText().charAt(i);
	    				else
	    					break;
	    			}
	    			selectedServiceTypesCodes.add(code);
	    		}
    		}
    	}
    	if(selectedServiceTypesCodes.isEmpty()){
    		Alert a = new Alert(AlertType.WARNING, "User must select at least one service type.");
    		a.setHeaderText("Invalid parameters selection");
			a.setTitle("Warning");
			((Stage)a.getDialogPane().getScene().getWindow()).getIcons().add(new Image(getClass().getResourceAsStream("eu_icon.png")));    		
    		a.showAndWait();
    		return;
    	}
    	TrustedListFacade.getInstance().getQuery().setSelectedServiceTypes(selectedServiceTypesCodes);
        App.setRoot("selectStatus");
    }
    
    @FXML
    /**
     * Switches scene to the "selectProviders" one
     * @throws IOException
     */
    private void switchToSelectProviders() throws IOException {
        App.setRoot("selectProviders");
    }
}