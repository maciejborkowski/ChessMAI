package uci;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class AdapterPool {
	boolean alive = true;
	LinkedList<UCIAdapter> unused = new LinkedList<UCIAdapter>();
	LinkedList<UCIAdapter> used = new LinkedList<UCIAdapter>();

	public void addAdapter(UCIAdapter adapter) {
		unused.add(adapter);
		adapter.uci();
		adapter.isready();
	}

	public synchronized UCIAdapter bindAdapter() throws Exception {
		if (!alive) {
			throw new Exception("Dead pool");
		}
		UCIAdapter adapter = null;
		while (adapter == null) {
			try {
				adapter = unused.getFirst();
			} catch (NoSuchElementException e) {
				if (used.isEmpty()) {
					throw new Exception("Empty pool");
				}
				Thread.sleep(100);
			}
		}

		unused.remove(adapter);
		used.add(adapter);
		return adapter;
	}

	public synchronized void releaseAdapter(UCIAdapter adapter) {
		if (!alive) {
			adapter.quit();
		}
		used.remove(adapter);
		unused.add(adapter);
	}

	public synchronized void kill() {
		alive = false;
		for (UCIAdapter adapter : unused) {
			adapter.quit();
		}
	}

	public int size() {
		return used.size() + unused.size();
	}
}
