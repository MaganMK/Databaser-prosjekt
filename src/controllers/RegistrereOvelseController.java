package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Core.Exercise;
import Core.ExerciseOnApp;
import Core.SimpleExercise;
import database.SQLConnector;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
		FadeTransition ft = new FadeTransition(Duration.millis(3000), this.feedback);
		ft.setFromValue(1);
		ft.setToValue(0);
		ft.play();
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
	@FXML TextField varighet, startTid, note, prestasjon, form;
	@FXML DatePicker sessionDate;
	private List<Exercise> exercises = new ArrayList<>();
	
	@FXML public void addSession() {
		if (exercises.isEmpty()) {
			writeSessionFeedback(false, "Det er ikke lagt inn noen øvelser i økten.");
			return;
		}
		int varighetValue, prestasjonValue, formValue;
		try {
			varighetValue = Integer.valueOf(varighet.getText());
			prestasjonValue = Integer.valueOf(prestasjon.getText());
			formValue = Integer.valueOf(form.getText());
		} catch(Exception e) {
			writeSessionFeedback(false, "Varighet, prestasjon og form-feltene må ha heltallsverdi");
			return;
		}
		LocalDate date = sessionDate.getValue();
		if (date == null) {
			writeSessionFeedback(false, "Oppgi dato");
			return;
		}
		if (startTid.getText().length() < 5) {
			writeSessionFeedback(false,"Oppgi starttidspunkt på formen tt:mm");
			return;
		}
		int hours, minutes;
		try {
			hours = Integer.valueOf(startTid.getText().substring(0, 2));
			minutes = Integer.valueOf(startTid.getText().substring(3, 5));
		}catch (Exception e) {
			writeSessionFeedback(false, "Oppgi starttidspunktet på økten på formen tt:mm");
			return;
		}
		if (hours <0 || hours > 23 || minutes <0 || minutes > 59) {
			writeSessionFeedback(false, "Oppgi starttidspunktet på økten på formen tt:mm");
			return;
		}
		String query = String.format("INSERT INTO Okt (`dato`, `tidspunkt`, `varighet`, `personlig_form`, `prestasjon`, `notat`) VALUES('%s-%s-%s', '%s:%s:00', %s, %s, %s, \"%s\" )", date.getYear(), date.getMonthValue(), date.getDayOfMonth(), hours, minutes, varighetValue ,formValue, prestasjonValue, note.getText());
		System.out.println(query);
		if (!SQLConnector.insert(query)) {
			writeSessionFeedback(false, "SQL feil, økten ble ikke lagt inn");
			return;
		}
		
		int oktID = 0;
		ResultSet rs;
		try {
			rs = SQLConnector.getResultSet("SELECT LAST_INSERT_ID()");
			if (rs.next()) {
				oktID = Integer.valueOf(rs.getString("last_insert_id()"));
			}
			else {
				writeSessionFeedback(false, "Fant ikke OktID");
				return;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			writeSessionFeedback(false, "noe gikk galt. Prøv igjen");
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			writeSessionFeedback(false, "noe gikk galt. Prøv igjen");
		}
		
		for (Exercise exercise : exercises) {
			if (exercise instanceof SimpleExercise) {
				SQLConnector.insert(String.format("INSERT INTO OvelseUtenApparatIOkt VALUES(\"%s\", \"%s\", %s)", exercise.getName(), ((SimpleExercise) exercise).getDescription(), oktID));
			}
			else if (exercise instanceof ExerciseOnApp) {
				SQLConnector.insert(String.format("INSERT INTO OverlsePaApparatIOkt VALUES(\"%s\", %s, %s, %s)", exercise.getName(), oktID, ((ExerciseOnApp) exercise).getWeight(), ((ExerciseOnApp) exercise).getSets()));
			}
		}
		writeSessionFeedback(true, "Økt med øvelsen er lagt til");
		exercises.clear();
	}
	
	
	//Legg ovelse i treningsokt
	@FXML TextField exerciseField,sets,weight;
	@FXML public void addExerciseToSession() {
		int setsValue;
		int weightValue ;
		try {
			setsValue = Integer.valueOf(sets.getText());
			weightValue = Integer.valueOf(weight.getText());
		}catch (Exception e) {
			e.printStackTrace();
			writeSessionFeedback(false, "Set og vekt feltene må ha tallverdier");
			return;
		}
		try {
			ResultSet rs = SQLConnector.getResultSet("SELECT * FROM Ovelse WHERE navn=\""+ exerciseField.getText().toLowerCase() +"\"");
			if (!rs.next()) {
				writeSessionFeedback(false, "Øvelsen " + exerciseField.getText().toLowerCase() + " finnes ikke i databasen. Legg til øvelsen først");
				return;
			}
			rs = SQLConnector.getResultSet("SELECT * FROM OvelsePaApparat WHERE ovelse_navn=\"" + exerciseField.getText().toLowerCase() + "\"");
			if (!rs.next()) {
				writeSessionFeedback(false, "Øvelsen " + exerciseField.getText().toLowerCase() + " finnes, men den er ikke koblet til et apparat.");
				return;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			writeSessionFeedback(false, "Noe gikk galt. Prøv igjen");
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			writeSessionFeedback(false, "Noe gikk galt. Prøv igjen");
			return;
		}
		exercises.add(new ExerciseOnApp(exerciseField.getText().toLowerCase(), setsValue, weightValue));
		writeSessionFeedback(true, "Øvelsen er lagret lokalt. Husk å lagre økten når du har lagt inn alle øvelsene.");
	}
	
	@FXML TextField simpleExerciseField, exerciseDescription;
	@FXML public void addSimpleExerciseToSession() {
		try {
			ResultSet rs = SQLConnector.getResultSet("SELECT * FROM Ovelse WHERE navn=\""+ simpleExerciseField.getText().toLowerCase() +"\"");
			if (!rs.next()) {
				writeSessionFeedback(false, "Øvelsen " + simpleExerciseField.getText().toLowerCase() + " finnes ikke i databasen. Legg til øvelsen først");
				return;
			}
			String query = "SELECT * FROM OvelsePaApparat WHERE ovelse_navn=" + simpleExerciseField.getText().toLowerCase() + "\"";
			System.out.println("addSimpleExerciseToSession query: "+query);
			rs = SQLConnector.getResultSet("SELECT * FROM OvelsePaApparat WHERE ovelse_navn=\"" + simpleExerciseField.getText().toLowerCase() + "\"");
			if (rs.next()) {
				writeSessionFeedback(false, "Øvelsen " + simpleExerciseField.getText().toLowerCase() + " er knyttet til et apparat.");
				return;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			writeSessionFeedback(false, "Noe gikk galt. Prøv igjen");
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			writeSessionFeedback(false, "Noe gikk galt. Prøv igjen");
			return;
		}
		exercises.add(new SimpleExercise(simpleExerciseField.getText().toLowerCase(), exerciseDescription.getText().toLowerCase()));
		writeSessionFeedback(true, "Øvelsen er lagret lokalt. Husk å lagre økten når du har lagt inn alle øvelsene.");
	}
	
	
	@FXML Text feedbackSession;
	public void writeSessionFeedback(boolean ok, String feedback) {
		if (ok) {
			this.feedbackSession.setFill(Color.BLACK);
		}
		else {
			this.feedbackSession.setFill(Color.RED);
		}
		this.feedbackSession.setText(feedback);
		FadeTransition ft = new FadeTransition(Duration.millis(3000), feedbackSession);
		ft.setFromValue(1);
		ft.setToValue(0);
		ft.play();
	}

}
