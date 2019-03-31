package ar.edu.itba.sia.gps.fillzone;

import java.util.Optional;

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
		Graph g = state.getGraph();
		
		if(g.getCurrentColor() == color) {
			return Optional.empty();
		}
		
		Graph newGraph = new Graph(g);
		newGraph.mergeIslands(color);
		State s = new FillZoneState(newGraph);
		
		return Optional.of(s);
	}
	
}
