package fillzone;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import gps.api.Rule;
import gps.api.State;

public enum Rules implements Rule {
	
	FILL_WHITE{
		@Override
		public Integer getCost() {
			return Integer.valueOf(COST_APPLIANCE);
		}

		@Override
		public String getName() {
			return "Fill with white";
		}

		@Override
		public Optional<State> apply(State state) {
			return this.applyRule((FillZoneState)state, Color.WHITE);
		}
	}, 
	
	FILL_RED{

		@Override
		public Integer getCost() {
			return Integer.valueOf(COST_APPLIANCE);
		}

		@Override
		public String getName() {
			return "Fill with red";
		}

		@Override
		public Optional<State> apply(State state) {
			return this.applyRule((FillZoneState)state, Color.RED);
		}
		
	},
	
	FILL_BLUE{

		@Override
		public Integer getCost() {
			return Integer.valueOf(COST_APPLIANCE);
		}

		@Override
		public String getName() {
			return "Fill with blue";
		}

		@Override
		public Optional<State> apply(State state) {
			return this.applyRule((FillZoneState)state, Color.BLUE);
		}
		
	},
	
	FILL_YELLOW{
		@Override
		public Integer getCost() {
			return Integer.valueOf(COST_APPLIANCE);
		}

		@Override
		public String getName() {
			return "Fill with yellow";
		}

		@Override
		public Optional<State> apply(State state) {
			return this.applyRule((FillZoneState)state, Color.YELLOW);
		}
	}, 
	
	FILL_PINK{

		@Override
		public Integer getCost() {
			return Integer.valueOf(COST_APPLIANCE);
		}

		@Override
		public String getName() {
			return "Fill with pink";
		}

		@Override
		public Optional<State> apply(State state) {
			return this.applyRule((FillZoneState)state, Color.PINK);
		}
		
	}, 
	
	FILL_GREEN{

		@Override
		public Integer getCost() {
			return Integer.valueOf(COST_APPLIANCE);
		}

		@Override
		public String getName() {
			return "Fill with green";
		}

		@Override
		public Optional<State> apply(State state) {
			return this.applyRule((FillZoneState)state, Color.GREEN);
		}
		
	};
	
	public final int COST_APPLIANCE = 1;
	
	public Optional<State> applyRule(FillZoneState state, Color color) {
		
		Color[][] b = state.getBoard();
		
		if(b[0][0].getValue() == color.getValue()) {
			return Optional.empty();
		}
		
		Color[][] newBoard = b.clone();
		
		Queue<Cell> q = new LinkedList<>();
		q.add(new Cell(0, 0));
		
		while(!q.isEmpty()) {
			
			Cell c = q.poll();
			newBoard[c.y][c.x] = color;
			
			if(c.y != 0 && newBoard[c.y - 1][c.x].getValue() == 
					b[0][0].getValue()) {
				q.add(new Cell(c.x, c.y - 1));
			}
			
			if(c.y != b.length - 1 && newBoard[c.y + 1][c.x].getValue() == 
					b[0][0].getValue()) {
				q.add(new Cell(c.x, c.y + 1));
			}
			
			if(c.x != 0 && newBoard[c.y][c.x - 1].getValue() ==
					b[0][0].getValue()) {
				q.add(new Cell(c.x - 1, c.y));
			}
			
			if(c.x != b[0].length - 1 && newBoard[c.y][c.x + 1].getValue() == 
					b[0][0].getValue()) {
				q.add(new Cell(c.x + 1, c.y));
			}
		}
		
		State s = new FillZoneState(newBoard);
		
		return Optional.of(s);
	}
	
}
