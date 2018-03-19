package controllers;

import database.SQLConnector;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultatLoggController {
	
	@FXML TextField resultExercise;
	@FXML TextArea resultView; //TODO parameter
	@FXML DatePicker fromDate, toDate;
	
	@FXML public void showResultLog() throws SQLException, ClassNotFoundException, ParseException {
        String exercise = resultExercise.getText();
        List<ArrayList<String>> session = SQLConnector.getSessionsDate(exercise);
        String str = "";
        // Specifies the date format
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        // Converts DatePicker -> String -> Date
        String fromDateString = String.valueOf(fromDate);
        Date dateFromDate = formatter.parse(fromDateString);
        String toDateString = String.valueOf(toDate);
        Date dateToDate = formatter.parse(toDateString);

        for (ArrayList<String> list : session) {
            // Checks date of this session
            Date date = formatter.parse(list.toString().substring(1,11));
            // Adds to str if inbetween specified dates
            if (date.after(dateFromDate) && date.before(dateToDate)) {
                str += list.toString() + "\n";
            }
        }
        resultView.setText(str);
    }

}
