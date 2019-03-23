package ar.edu.itba.sia.gps.fillzone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import ar.edu.itba.sia.gps.api.Rule;
import ar.edu.itba.sia.gps.api.State;

public enum Rules implements Rule {
	FILL_WHITE {
		@Override
		public Optional<State> apply(State state) {
			return this.applyRule((FillZoneState)state, Color.WHITE);
		}
	}, 
	FILL_RED {
		@Override
		public Optional<State> apply(State state) {
			return this.applyRule((FillZoneState)state, Color.RED);
		}
	},
	FILL_BLUE {
		@Override
		public Optional<State> apply(State state) {
			return this.applyRule((FillZoneState)state, Color.BLUE);
		}
	},
	FILL_YELLOW {
		@Override
		public Optional<State> apply(State state) {
			return this.applyRule((FillZoneState)state, Color.YELLOW);
		}
	}, 
	FILL_PINK {
		@Override
		public Optional<State> apply(State state) {
			return this.applyRule((FillZoneState)state, Color.PINK);
		}
	}, 
	FILL_GREEN {
		@Override
		public Optional<State> apply(State state) {
			return this.applyRule((FillZoneState)state, Color.GREEN);
		}
	};
	
	public final int COST_APPLIANCE = 1;
	
	@Override
	public Integer getCost() {
		return Integer.valueOf(COST_APPLIANCE);
	}

	@Override
	public String getName() {
		return "Fill with" + this.toString();
	}
	
	public Optional<State> applyRule(FillZoneState state, Color color) {	
		Color[][] b = state.getBoard();
		
		if(b[0][0] == color) {
			return Optional.empty();
		}
		
		Color[][] newBoard = new Color[b.length][];
		for (int i = 0; i < b.length; i++) {
			newBoard[i] = Arrays.copyOf(b[i], b[i].length);
	    }
		
		List<Cell> checked = new ArrayList<>();
		Queue<Cell> q = new LinkedList<>();
		q.add(new Cell(0, 0));
		
		while(!q.isEmpty()) {
			Cell c = q.poll();
			newBoard[c.y][c.x] = color;
			
			if(c.y != 0 && newBoard[c.y - 1][c.x] == b[0][0]) {
				Cell neighbour = new Cell(c.x, c.y - 1);
				if (!checked.contains(neighbour))
					q.add(neighbour);
			}
			
			if(c.y != b.length - 1 && newBoard[c.y + 1][c.x] == b[0][0]) {
				Cell neighbour = new Cell(c.x, c.y + 1);
				if (!checked.contains(neighbour))
					q.add(neighbour);
			}
			
			if(c.x != 0 && newBoard[c.y][c.x - 1] == b[0][0]) {
				Cell neighbour = new Cell(c.x - 1, c.y);
				if (!checked.contains(neighbour))
					q.add(neighbour);
			}
			
			if(c.x != b[0].length - 1 && newBoard[c.y][c.x + 1] == b[0][0]) {
				Cell neighbour = new Cell(c.x + 1, c.y);
				if (!checked.contains(neighbour))
					q.add(neighbour);
			}
			
			checked.add(c);
		}
		
		State s = new FillZoneState(newBoard);
		
		return Optional.of(s);
	}
	
}
