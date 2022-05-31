package proseccoCoding.TLN.control;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import proseccoCoding.TLN.App;
import proseccoCoding.TLN.model.TrustedListFacade;
import proseccoCoding.TLN.model.Provider;

/**
 * 
 * Controller of the "selectProviders" section
 *
 */
public class SelectProvidersController {

	@FXML
	/**
	 * VBox object used to contain all the CheckBoxes used to let the user select his providers of interest
	 */
	private VBox providersPane;
	/**
	 * CheckBox used to select/deselect all the providers CheckBoxes at the same time
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
	 * Method called when SelectProvidersController is loaded. 
	 * This method adds to providersPane the selectAll CheckBox and a CheckBox for each provider 
	 * which country was previously selected, all with their ChangeListener.
	 */
	private void initialize() {
		// selectAll CheckBox is created 
		selectAll = new CheckBox("Select All");
		providersPane.getChildren().add(selectAll);
		providersPane.getChildren().add(new Label("————————————————————————————"));		
		
		// a ChangeListener is added to the selectAll CheckBox properties 
		selectAll.selectedProperty().addListener(selectAllListener);
		selectAll.indeterminateProperty().addListener(selectAllListener);

		// a CheckBox is added with a ChangeListener for each provider which country was previously selected
		for (Provider p : TrustedListFacade.getQuery().getAvailableProviders()) {
			CheckBox cb = new CheckBox(p.getCode()+" \n﹂"+p.getName());
			cb.selectedProperty().addListener(checkBoxListener);
			providersPane.getChildren().add(cb);
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
    	for(Node node : providersPane.getChildren()) {
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
     * Switches scene to the "selectCountries" one
     * @throws IOException
     */
    private void switchToSelectCountries() throws IOException {
        App.setRoot("selectCountries");
    }
    
    @FXML
    /**
     * Switches scene to the "selectServices".
     * This method also tracks down the selected providers via checking the status of the CheckBoxs and sets selectedProviders in TrustedListFacade's Query.
     * @throws IOException
     */
    private void switchToSelectServices() throws IOException {
    	ArrayList<String> selectedProvidersCodes = new ArrayList<String>();
    	for(Node node : providersPane.getChildren()) {
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
	    			selectedProvidersCodes.add(code);
	    		}
    		}
    	}
    	if(selectedProvidersCodes.isEmpty())
    		return;
    	TrustedListFacade.getQuery().setSelectedProviders(selectedProvidersCodes);
        App.setRoot("selectServices");
    }
}