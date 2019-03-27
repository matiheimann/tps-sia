package ar.edu.itba.sia.gps.fillzone;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.api.State;

public class ColorHeuristic implements Heuristic {

	@Override
	public Integer getValue(State state) {
		FillZoneState fState = (FillZoneState) state;
		Color[][] board = fState.getBoard();
		boolean[][] visited = new boolean[board.length][board[0].length];
		Color baseColor = board[0][0];
		Map<Color, Boolean> neighbourColors = new HashMap<>();
		for(Color c : Color.values()) {
			neighbourColors.put(c, false);
		}
		neighbourColors.put(baseColor, true);
		navigate(0, 0, board, visited, baseColor, neighbourColors);
		int n = 0;
		for(Entry<Color, Boolean> e : neighbourColors.entrySet()) {
			if(e.getValue()) {
				n++;
			}
		}
		return n-1;
	}
	
	private void navigate(int x, int y, Color[][] board, boolean[][] visited, Color baseColor, Map<Color, Boolean> neighbourColors) {
		visited[y][x] = true;
		if(!board[y][x].equals(baseColor)) {
			neighbourColors.put(board[y][x], true);
			return;
		}
		if((x + 1 < board[0].length) && !visited[y][x+1]) {
			navigate(x+1, y, board, visited, baseColor, neighbourColors);
		}
		if((x - 1 >= 0) && !visited[y][x-1]) {
			navigate(x-1, y, board, visited, baseColor, neighbourColors);
		}
		if((y + 1 < board.length) && !visited[y+1][x]) {
			navigate(x, y+1, board, visited, baseColor, neighbourColors);
		}
		if((y - 1 >= 0) && !visited[y-1][x]) {
			navigate(x, y-1, board, visited, baseColor, neighbourColors);
		}
		return;
	}

}
