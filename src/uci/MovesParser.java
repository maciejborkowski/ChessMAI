package uci;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MovesParser {

	private static final HashMap<Character, Integer> columnMap = new HashMap<>();
	private static final HashMap<Character, Integer> rowMap = new HashMap<>();
	private static final HashMap<Character, Integer> pieceMap = new HashMap<>();

	static {
		columnMap.put('a', 0);
		columnMap.put('b', 1);
		columnMap.put('c', 2);
		columnMap.put('d', 3);
		columnMap.put('e', 4);
		columnMap.put('f', 5);
		columnMap.put('g', 6);
		columnMap.put('h', 7);

		rowMap.put('1', 7);
		rowMap.put('2', 6);
		rowMap.put('3', 5);
		rowMap.put('4', 4);
		rowMap.put('5', 3);
		rowMap.put('6', 2);
		rowMap.put('7', 1);
		rowMap.put('8', 0);

		pieceMap.put(' ', 0);
		pieceMap.put('n', 1);
		pieceMap.put('b', 2);
		pieceMap.put('r', 3);
		pieceMap.put('q', 4);
	}

	public static void parse(final String moveString, final int[] move) {
		move[0] = columnMap.get(moveString.charAt(0));
		move[1] = rowMap.get(moveString.charAt(1));
		move[2] = columnMap.get(moveString.charAt(2));
		move[3] = rowMap.get(moveString.charAt(3));
		if (moveString.length() == 5) {
			move[4] = pieceMap.get(moveString.charAt(4));
		} else {
			move[4] = pieceMap.get(' ');
		}
	}

	public static String parse(final int[] move) {
		char[] moveChars = new char[5];
		moveChars[0] = getKeyByValue(columnMap, move[0]);
		moveChars[1] = getKeyByValue(rowMap, move[1]);
		moveChars[2] = getKeyByValue(columnMap, move[2]);
		moveChars[3] = getKeyByValue(rowMap, move[3]);
		moveChars[4] = getKeyByValue(pieceMap, move[4]);
		return new String(moveChars);
	}

	public static Character getKeyByValue(Map<Character, Integer> map, Integer value) {
		for (Entry<Character, Integer> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

}
