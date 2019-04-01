package ar.edu.itba.sia.gps.fillzone;

import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.api.State;

public class IsleCountHeuristic implements Heuristic {

	@Override
	public Integer getValue(State state) {
		return ((FillZoneState) state).getGraph().getIsleCount() - 1;
	}
	
	@Override
	public String toString() {
		return "Isle Count Heuristic";
	}

}
