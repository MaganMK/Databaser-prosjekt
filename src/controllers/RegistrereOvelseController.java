package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.SQLConnector;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegistrereOvelseController {

	//Legg til nytt apparat
	@FXML Text feedback;
	@FXML TextField newApparat;
	
	@FXML public void addApparat() throws ClassNotFoundException, SQLException {
		System.out.println("AddApparat detected");
		String apparatName = newApparat.getText().toLowerCase();
		
		ResultSet rs = SQLConnector.getResultSet("SELECT * FROM Apparat WHERE apparat_navn="+apparatName);
		if (rs.next()) {
			feedback.setText("Apparatet er lagt inn fra før");
		}
		else {
			if(!SQLConnector.addApparat(apparatName)) {
				feedback.setText("Something went wrong..");
			}
			else {
				feedback.setText("");
			}
		}
		
	}
	
	
	//Legg til ny ovelse
	@FXML TextField newOvelse;
	@FXML public void addOvelse() {
		
	}
	
	//Legge ovelse til apparat
	@FXML public void addExerciseToApparat() {
		
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
	
	
	
	
	@FXML TextArea status;
	public void updateStatus() {
		
	}

}
