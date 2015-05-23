package chess.player;

public abstract class MetaheuristicPlayer extends Player {
	public static enum Mode {
		ADVENTUROUS, GREEDY
	}

	protected Mode mode = Mode.GREEDY;

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

}
