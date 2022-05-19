package proseccoCoding.TLN.control;

import java.io.IOException;



import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import proseccoCoding.TLN.App;
import proseccoCoding.TLN.model.UrlRequester;

public class PrimaryController {
	@FXML
	private VBox nationPane;
	private SimpleBooleanProperty isSelected;
	private SimpleBooleanProperty isIndeterminate;
	private CheckBox selectAll;
	
	ChangeListener<Boolean> selectAllListener = new ChangeListener<Boolean>(){
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	    	selectAllChanged(newValue);
	    }
	};
	ChangeListener<Boolean> checkBoxListener = new ChangeListener<Boolean>(){
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	    	checkBoxChanged(newValue);
	    }
	};
	
	@FXML
	private void initialize() {
		
	// aggiungo select al vbox
		// inizializzo le boolean property
		isSelected = new SimpleBooleanProperty(false);
		isIndeterminate = new SimpleBooleanProperty(false);
		// creo e aggiungo un checkbox a runtime 
		selectAll = new CheckBox("Select All");
		nationPane.getChildren().add(selectAll);
		
		// aggiungo il change listener alla proprietà isSelected del checkbox 
		selectAll.selectedProperty().addListener(selectAllListener);
		selectAll.indeterminateProperty().addListener(selectAllListener);
		    	
		// binding dell'attributo boolean isSelected con la proprietà del checkbox
		isSelected.bind(selectAll.selectedProperty());
		isIndeterminate.bind(selectAll.indeterminateProperty());

		System.out.println("Starting conditions");
    	System.out.println("Attrib: ind "+isIndeterminate.get() +" |select "+isSelected.get());
    	System.out.println("CheckB: ind "+ selectAll.isIndeterminate() +" |select "+ selectAll.isSelected());

    	// aggiungo nazioni con ascoltatore unico
		for (String s : UrlRequester.requestNations()) {
			CheckBox cb = new CheckBox(s);
			cb.selectedProperty().addListener(checkBoxListener);
			nationPane.getChildren().add(cb);
		}
	}
	
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    private void selectAllChanged(Boolean value) {
    	System.out.println("selectAllChanged invoked, newValue: "+value);
    	System.out.println("Attrib: ind "+isIndeterminate.get() +" |select "+isSelected.get());
    	System.out.println("CheckB: ind "+ selectAll.isIndeterminate() +" |select "+ selectAll.isSelected());
    	if(selectAll.isIndeterminate())
    		return;
    	// seleziono ogni checkbox nel Vbox
    	for(Node node : nationPane.getChildren()) {
    		if(node.getClass().equals(CheckBox.class) && !node.equals(selectAll)) {
	    		CheckBox cb = (CheckBox)node;
	    		cb.selectedProperty().set(selectAll.isSelected());
    		}
    	}
    }
    
    private void checkBoxChanged(Boolean newValue) {
    	if(newValue == false && selectAll.isSelected())
    		selectAll.setIndeterminate(true);
    }
}
