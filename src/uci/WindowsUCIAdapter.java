package uci;

import java.io.IOException;
import java.util.Scanner;

public class WindowsUCIAdapter extends UCIAdapter {
	//private String processName = "stockfish-6-64.exe";
	//private String processName = "komodo6-64bit.exe";
	private String processName = "Firenzina.exe";
	byte[] buffer = new byte[1000];
	private Scanner scanner;

	@SuppressWarnings("resource")
	public WindowsUCIAdapter() {
		runtime = Runtime.getRuntime();
		try {
			process = runtime.exec(processName);
		} catch (IOException e) {
			System.out.println("Could not execute " + processName);
			e.printStackTrace();
		}
		processInput = process.getOutputStream();
		processOutput = process.getInputStream();
		scanner = new Scanner(processOutput).useDelimiter("\n");
	}

	protected void sendCommand(String command) {
		try {
			processInput.write((command + "\n").getBytes());
			processInput.flush();
			System.out.println("SENT: \t\t" + command);
		} catch (IOException e) {
			System.out.println("COULD NOT SEND COMMAND TO UCI");
			e.printStackTrace();
		}
	}

	protected String receiveAnswer() {
		String answer = scanner.hasNext() ? scanner.next() : "";
		System.out.print("ANSWER: \t" + answer);
		return answer;
	}

}
