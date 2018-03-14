package Core;

public class SimpleExercise extends Exercise {
	private String description;

	public SimpleExercise(String name, String description) {
		super(name);
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

}
