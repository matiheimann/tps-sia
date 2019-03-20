package fillzone;

import gps.api.State;

public class FillZoneState implements State {
	
	private Color[][] board;
	
	@Override
	public String getRepresentation() {
		StringBuffer representation = new StringBuffer("");
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				representation.append(board[i][j]);
				representation.append(" ");
			}
			representation.append('\n');
		}
		return representation.toString();
	}
	
	@Override
	public boolean equals(Object state) {
		
		if(state == null)
			return false;
		
		if(this == state)
			return true;
		
		if(state.getClass() != this.getClass())
			return false;
		
		FillZoneState fzs = (FillZoneState) state;
		
		if(this.board.length != fzs.board.length ||
				this.board[0].length != fzs.board[0].length)
			return false;
		
		for(int i = 0; i < this.board.length; i++)
			for(int j = 0; j < this.board[0].length; j++)
				if(this.board[i][j] != fzs.board[i][j])
					return false;
		
		return true;
		
	}
	
	public Color[][] getBoard() {
		return board;
	}

}
