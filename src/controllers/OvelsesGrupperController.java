package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class OvelsesGrupperController {
	
	//Lag ny ovelsesgruppe
	@FXML TextField newGroupField;

	@FXML public void createGroup() {
		
	}
	
	
	
	//Legg ovelse inn i gruppe
	@FXML TextField addExerciseField,
					addToGroupField;
	
	@FXML public void addExerciseToGroup() {
		
	}
	
	
	
	//Vise liknende ovelser i listview
	@FXML TextField exerciseWithGroupField;
	@FXML ListView<?> sameGroupView; //TODO parameter
	
	@FXML public void showExerciseGroup() {
		
	}

}
