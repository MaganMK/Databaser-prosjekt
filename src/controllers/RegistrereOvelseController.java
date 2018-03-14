package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.SQLConnector;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class RegistrereOvelseController {

	
	@FXML Text feedback;
	@FXML TextField newApparat;
	//Legg til nytt apparat
	@FXML public void addApparat() {
		String apparatName = newApparat.getText().toLowerCase();
		try {
			ResultSet rs = SQLConnector.getResultSet("SELECT * FROM Apparat WHERE apparat_navn=\""+apparatName +"\"");
			if (rs.next()) {
				feedback.setFill(Color.RED);
				feedback.setText("Apparatet er lagt inn fra før.");
			}
			else {
				String query = "INSERT INTO Apparat VALUES (\"" +apparatName+ "\")";
				if(!SQLConnector.insert(query)) {
					feedback.setFill(Color.RED);
					feedback.setText("Kunne ikke legge inn apparatet. Prøv igjen.");
				}
				else {
					feedback.setFill(Color.BLACK);
					feedback.setText("Apparatet " + apparatName + " ble lagt til");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void writeFeedback(boolean ok, String feedback) {
		if (ok) {
			this.feedback.setFill(Color.BLACK);
		}
		else {
			this.feedback.setFill(Color.RED);
		}
		this.feedback.setText(feedback);
	}
	
	
	//Legg til ny ovelse
	@FXML TextField newOvelse;
	@FXML public void addOvelse() {
		String exercise = newOvelse.getText().toLowerCase();
		try {
			ResultSet rs = SQLConnector.getResultSet("SELECT * FROM Ovelse WHERE navn=\""+ exercise +"\"");
			if (rs.next()) {
				writeFeedback(false, "Øvelsen er lagt inn fra før.");
				return;
			}
			String query = "INSERT INTO Ovelse VALUES (\"" +exercise+ "\")";
			if(!SQLConnector.insert(query)) {
				writeFeedback(false, "Kunne ikke legge inn øvelsen. Prøv igjen.");
			}
			else {
				writeFeedback(true,"Øvelsen " + exercise + " har blitt lagt til" );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	// Connects Exercise to Apparat
	@FXML public void addExerciseToApparat() {
		String exercise = newOvelse.getText().toLowerCase();
		String apparat = newApparat.getText().toLowerCase();
		try {
			// Checks if relation already exists
			ResultSet rs = SQLConnector.getResultSet(String.format("SELECT * FROM OvelsePaApparat WHERE ovelse_navn=\"%s\" AND apparat_navn=\"%s\"", exercise, apparat));
			if (rs.next()) {
				writeFeedback(false, String.format("Øvelsen %s er allerede knyttet til apparatet %s", exercise, apparat));
				return;
			}
			// Checks if exercise exists, adds it if not
			ResultSet databaseExercise = SQLConnector.getResultSet("SELECT * FROM Ovelse WHERE navn=\""+ exercise +"\"");
			if (!databaseExercise.next()) {
				if (!SQLConnector.insert("INSERT INTO Ovelse VALUES (\"" +exercise+ "\")")) {
					writeFeedback(false, "Kunne ikke legge til apparat til økt. Prøv igjen.");
				}
			}
			// Checks if apparat exists, adds it if not
			ResultSet databaseApparat = SQLConnector.getResultSet("SELECT * FROM Apparat WHERE apparat_navn=\""+apparat +"\"");
			if (!databaseApparat.next()) {
				if (!SQLConnector.insert("INSERT INTO Apparat VALUES (\"" +apparat+ "\")")) {
					writeFeedback(false, "Kunne ikke legge til apparat til økt. Prøv igjen.");
				}
			}
			// Adds the relation to the database
			String query = String.format("INSERT INTO OvelsePaApparat VALUES ( \"%s\" , \"%s\" )", exercise, apparat);
			if (SQLConnector.insert(query)) {
				writeFeedback(true, "Øvelsen "+exercise + " er nå knyttet til apparatet " + apparat);
			}
			else {
				writeFeedback(false, "Noe gikk galt. Prøv igjen.");
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
