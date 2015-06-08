package metaheuristic.genetic;
import java.util.Comparator;

public class MoveCost implements Comparator<MoveCost>{
	private int[] move;
	private double cost;
	public MoveCost()
	{
		
	}

	public MoveCost(int[] move, double cost) {
		this.move = move;
		this.cost = cost;
	}
	public int compare(MoveCost e1, MoveCost e2) {
		int result = (int)(e1.cost - e2.cost)*(-1);
		return result;
	}
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
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
		builder.append(cost);
		builder.append(" ");
		return builder.toString();
	}
}
