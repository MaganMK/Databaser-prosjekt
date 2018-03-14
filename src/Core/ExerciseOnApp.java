package Core;

public class ExerciseOnApp extends Exercise {
	
	private int sets, weight;

	public ExerciseOnApp(String name, int sets, int weight) {
		super(name);
		this.sets = sets;
		this.weight = weight;
	}
	
	public int getSets() {
		return sets;
	}

	public int getWeight() {
		return weight;
	}
}
