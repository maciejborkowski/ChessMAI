package chess;

public class AIPlayer extends Player {

	@Override
	public void think() {
		System.out.println(color + " THINKS");
	}

	@Override
	public int[] getMove() {
		return null;
	}
}
