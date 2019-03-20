package fillzone;

import gps.api.Heuristic;
import gps.api.State;

public class NeighbourHeuristic implements Heuristic {

	@Override
	public Integer getValue(State state) {
		FillZoneState fState = (FillZoneState) state;
		Color[][] board = fState.getBoard();
		boolean[][] visited = new boolean[board.length][board[0].length];
		Color baseColor = board[0][0];
		return board.length * board[0].length - navigate(0, 0, board, visited, baseColor);
	}
	
	private int navigate(int x, int y, Color[][] board, boolean[][] visited, Color baseColor) {
		visited[x][y] = true;
		if(!board[x][y].equals(baseColor)) {
			return 0;
		}
		int n = 1;
		if((x + 1 < board.length) && !visited[x+1][y]) {
			n += navigate(x+1, y, board, visited, baseColor);
		}
		if((x - 1 >= 0) && !visited[x-1][y]) {
			n += navigate(x-1, y, board, visited, baseColor);
		}
		if((y + 1 < board[0].length) && !visited[x][y+1]) {
			n += navigate(x, y+1, board, visited, baseColor);
		}
		if((y - 1 >= 0) && !visited[x][y-1]) {
			n += navigate(x, y-1, board, visited, baseColor);
		}
		return n;
	}

}
