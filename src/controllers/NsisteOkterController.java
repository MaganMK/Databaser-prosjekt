package controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.SQLConnector;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NsisteOkterController {
	
	@FXML TextField nField;
	@FXML TextArea nExercisesView;
	
	@FXML public void showNsessions() throws ClassNotFoundException, SQLException {
		List<ArrayList<String>> session = SQLConnector.getAllSessions();
		int n = Integer.valueOf(nField.getText());
		List<ArrayList<String>> nSession = new ArrayList<>(session.subList(session.size()-n, session.size()));
		String str = "";
		for (ArrayList<String> list : nSession) {
			str += list.toString() + "\n";
		}
		nExercisesView.setText(str);
	}

}
