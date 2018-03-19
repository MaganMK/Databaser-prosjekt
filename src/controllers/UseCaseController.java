package controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import database.SQLConnector;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class UseCaseController {

	
	@FXML Text numberOfTimes, mostUsedExercise;
	
	
	@FXML
	public void findMostUsedExercise() throws ClassNotFoundException, SQLException
	{
		List<String> allExercises = SQLConnector.getAllExercises();
		Map<String, Long> allExercisesWithCount = allExercises.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		
		long count = 0;
		String result = "";
		
		for (String name : allExercisesWithCount.keySet())
		{
			System.out.println(count);
			if(allExercisesWithCount.get(name) > count)
			{
				count = allExercisesWithCount.get(name);
				result = name;
			}
		}
		
		numberOfTimes.setText("" + count);
		mostUsedExercise.setText(result);
	}
}







