package uci;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class UCIAdapter {
	protected Runtime runtime;
	protected Process process;
	protected OutputStream processInput;
	protected InputStream processOutput;

	public void uci() {
		String command = "uci";
		sendCommand(command);
	}

	public void isready() {
		String command = "isready";
		sendCommand(command);
		StringBuilder builder = new StringBuilder();
		String answer;
		do {
			answer = receiveAnswer();
			builder.append(answer);
		} while (!answer.contains("readyok"));
	}

	public void ucinewgame() {
		String command = "ucinewgame";
		sendCommand(command);
	}

	public void position(String positions) {
		String command = "position startpos moves " + positions;
		sendCommand(command);
	}

	public String go(int thinkingTime) {
		String command = "go movetime " + String.valueOf(thinkingTime);
		sendCommand(command);
		String answer;
		do {
			answer = receiveAnswer();
		} while (!answer.contains("bestmove"));
		return answer.split(" ")[1];

	}

	public void quit() {
		String command = "quit";
		sendCommand(command);
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			System.out.println("INTERRUPTED WHILE WAITING FOR PROCESS TO END");
			e.printStackTrace();
		}
	}

	protected abstract void sendCommand(String command);

	protected abstract String receiveAnswer();
}
