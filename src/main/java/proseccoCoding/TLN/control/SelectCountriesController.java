package proseccoCoding.TLN.control;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Collections;

import proseccoCoding.TLN.App;
import proseccoCoding.TLN.model.TrustedListFacade;

/**
 * 
 * Controller of the "selectCountries" section
 *
 */
public class SelectCountriesController {
	
	@FXML
	/**
	 * VBox object used to contain all the CheckBoxes used to let the user select his countries of interest
	 */
	private VBox countriesPane;
	/**
	 * CheckBox used to select/deselect all the countries CheckBoxes at the same time
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
	 * Method called when SelectCountriesController is loaded. 
	 * This method adds to countriesPane the selectAll CheckBox and a CheckBox for each country, all with their ChangeListener.
	 */
	private void initialize() {    
		// selectAll CheckBox is created 
		selectAll = new CheckBox("Select All");
		countriesPane.setSpacing(5);
		countriesPane.setPadding(new Insets(5));
		countriesPane.getChildren().add(selectAll);
		countriesPane.getChildren().add(new Separator());		
		
		// a ChangeListener is added to the selectAll CheckBox properties 
		selectAll.selectedProperty().addListener(selectAllListener);
		selectAll.indeterminateProperty().addListener(selectAllListener);
		
		ArrayList<String> countriesNames = new ArrayList<String>();
		for (Pair<String, String> p : TrustedListFacade.getData().printCountries()) 
			countriesNames.add(p.getKey()+" - "+p.getValue());
		
		Collections.sort(countriesNames);

    	// a CheckBox is added with a ChangeListener for each country
		for (String s : countriesNames) {
			CheckBox cb = new CheckBox(s);
			cb.selectedProperty().addListener(checkBoxListener);
			countriesPane.getChildren().add(cb);
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
    	for(Node node : countriesPane.getChildren()) {
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
     * Switches scene to the "home" one. If a Query has been created it gets ended.
     * @throws IOException
     */
    private void switchToHome() throws IOException {
    	if(TrustedListFacade.getQuery()!=null)
    		TrustedListFacade.endQuery();
        App.setRoot("home");
    }
    
    @FXML
    /**
     * Switches scene to the "selectProviders" one.
     * This method also tracks down the selected countries via checking the status of the CheckBoxs and sets selectedCountries in TrustedListFacade's Query.
     * If no country was selected, a warning alert is set to inform the user of his mistake.
     * @throws IOException
     */
    private void switchToSelectProviders() throws IOException {
    	ArrayList<String> selectedCountryCodes = new ArrayList<String>();
    	for(Node node : countriesPane.getChildren()) {
    		if(node.getClass().equals(CheckBox.class) && !node.equals(selectAll)) {
	    		CheckBox cb = (CheckBox)node;
	    		if(cb.isSelected()==true)
	    			selectedCountryCodes.add(cb.getText().charAt(0) +""+ cb.getText().charAt(1));
    		}
    	}
    	if(selectedCountryCodes.isEmpty()) {
    		Alert a = new Alert(AlertType.WARNING, "User must select at least one country.");
    		a.setHeaderText("Invalid parameters selection");
			a.setTitle("Warning");
			((Stage)a.getDialogPane().getScene().getWindow()).getIcons().add(new Image(getClass().getResourceAsStream("eu_icon.png")));    		
    		a.showAndWait();
    		return;
    	}
    	TrustedListFacade.startQuery(selectedCountryCodes);
        App.setRoot("selectProviders");
    }
}