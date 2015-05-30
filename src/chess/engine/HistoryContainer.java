package chess.engine;

public interface HistoryContainer {

	public String extract();

	public void rewrite(String history);
}
