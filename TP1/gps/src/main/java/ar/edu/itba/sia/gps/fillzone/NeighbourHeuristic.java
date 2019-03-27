package ar.edu.itba.sia.gps.fillzone;

import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.api.State;

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
		visited[y][x] = true;
		if(!board[y][x].equals(baseColor)) {
			return 0;
		}
		int n = 1;
		if((x + 1 < board[0].length) && !visited[y][x+1]) {
			n += navigate(x+1, y, board, visited, baseColor);
		}
		if((x - 1 >= 0) && !visited[y][x-1]) {
			n += navigate(x-1, y, board, visited, baseColor);
		}
		if((y + 1 < board.length) && !visited[y+1][x]) {
			n += navigate(x, y+1, board, visited, baseColor);
		}
		if((y - 1 >= 0) && !visited[y-1][x]) {
			n += navigate(x, y-1, board, visited, baseColor);
		}
		return n;
	}

}
