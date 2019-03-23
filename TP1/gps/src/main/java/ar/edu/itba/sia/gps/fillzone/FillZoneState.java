package ar.edu.itba.sia.gps.fillzone;

import java.util.Arrays;

import ar.edu.itba.sia.gps.api.State;

public class FillZoneState implements State {
	
	private Color[][] board;
	
	public FillZoneState(Color[][] board) {
		this.board = board;
	}
	
	@Override
	public String getRepresentation() {
		StringBuffer representation = new StringBuffer("");
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				representation.append(board[i][j].ordinal());
				representation.append(" ");
			}
			representation.append('\n');
		}
		return representation.toString();
	}

	public Color[][] getBoard() {
		return this.board;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(board);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FillZoneState other = (FillZoneState) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		return true;
	}

}
