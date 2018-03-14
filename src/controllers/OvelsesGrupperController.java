package controllers;

import java.sql.SQLException;
import java.util.List;

import database.SQLConnector;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class OvelsesGrupperController {

	
	//Lag ny ovelsesgruppe
	@FXML TextField newGroupField;

	@FXML public void createGroup() throws SQLException, ClassNotFoundException {
		String newGroup = newGroupField.getText();
		SQLConnector.insertToTable("Ovelsesgruppe",newGroup);
	}
	
	
	
	//Legg ovelse inn i gruppe
	@FXML TextField addExerciseField,
					addToGroupField;
	
	@FXML public void addExerciseToGroup() throws ClassNotFoundException, SQLException {
		String exercise = addExerciseField.getText();
		String group = addToGroupField.getText();
		SQLConnector.addExerciseToGroup(exercise, group);
	}
	
	
	
	//Vise liknende ovelser i listview
	@FXML TextField exerciseWithGroupField;
	@FXML TextField sameGroupView; //TODO parameter
	
	@FXML public void showExerciseGroup() throws ClassNotFoundException, SQLException {
		String group = exerciseWithGroupField.getText();
		
		List<String> exercises = SQLConnector.getExercisesInGroup(group);
		
		sameGroupView.setText(exercises.toString());
	}

}










