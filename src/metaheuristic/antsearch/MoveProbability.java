package metaheuristic.antsearch;

public class MoveProbability {
	private int[] move;
	private double probability;

	public MoveProbability(int[] move, double probability) {
		this.move = move;
		this.probability = probability;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public int[] getMove() {
		return move;
	}

	public void setMove(int[] move) {
		this.move = move;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int val : move) {
			builder.append(val);
			builder.append(" ");
		}
		builder.append(probability);
		builder.append(" ");
		return builder.toString();
	}
}
