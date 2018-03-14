package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RegistrereOvelseController {
	
	//Legg til nytt apparat
	@FXML TextField newApparat;
	
	@FXML public void addApparat() {
		
	}
	
	
	//Legg til ny ovelse
	@FXML TextField newOvelse;
	
	@FXML public void addOvelse() {
		
	}
	
	
	//Lag ny treningsokt
	@FXML TextField sessionName, varighet, startTid, note, prestasjon, form;
	@FXML DatePicker sessionDate;
	
	@FXML public void addSession() {
		
	}
	
	
	//Legg ovelse i treningsokt
	@FXML TextField sessionField,
					exerciseField,
					sets,
					weight;
	
	@FXML public void addExerciseToSession() {
		
	}
	
	@FXML TextField simpleExerciseField,exerciseDescription ;
	
	@FXML public void addSimpleExerciseToSession() {
		
	}
	
	
	//Legge ovelse til apparat
	@FXML public void addExerciseToApparat() {
		
	}
	
	@FXML TextArea status;
	public void updateStatus() {
		
	}

}
