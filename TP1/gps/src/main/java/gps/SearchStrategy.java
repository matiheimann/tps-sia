package gps;

import java.util.Comparator;

public enum SearchStrategy implements Comparator<GPSNode> {
	BFS {
		@Override
		public int compare(GPSNode o1, GPSNode o2) {
			return Integer.compare(o1.getDepth(), o2.getDepth());
		}
	}, 
	DFS {
		@Override
		public int compare(GPSNode o1, GPSNode o2) {
			return -1 * Integer.compare(o1.getDepth(), o2.getDepth());
		}
	}, 
	IDDFS {
		@Override
		public int compare(GPSNode o1, GPSNode o2) {
			return DFS.compare(o1, o2);
		}
	}, 
	GREEDY {
		@Override
		public int compare(GPSNode o1, GPSNode o2) {
			return BFS.compare(o1, o2);
		}
	}, 
	ASTAR {
		@Override
		public int compare(GPSNode o1, GPSNode o2) {
			return Integer.compare(o1.getCost() + o1.getHeuristicValue(), o2.getCost() + o2.getHeuristicValue());
		}
	};
}
