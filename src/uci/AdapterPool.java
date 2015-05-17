package uci;

import java.util.LinkedList;

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
		UCIAdapter adapter = unused.getFirst();
		while (adapter == null) {
			try {
				if (used.isEmpty()) {
					throw new Exception("Empty pool");
				}
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			adapter = unused.getFirst();
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
}
