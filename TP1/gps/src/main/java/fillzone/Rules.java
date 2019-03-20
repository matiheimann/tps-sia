package fillzone;

import java.util.Optional;

import gps.api.Rule;
import gps.api.State;

public enum Rules implements Rule{
	
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
		FillZoneState fzs = (FillZoneState) state;
		if(fzs.getRepresentation().charAt(0) - '0' == color.getValue()) {
			return Optional.empty();
		}
		return null;
	}
	
}
