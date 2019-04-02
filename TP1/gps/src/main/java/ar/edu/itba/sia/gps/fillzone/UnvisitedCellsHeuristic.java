package ar.edu.itba.sia.gps.fillzone;

import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.api.State;

public class UnvisitedCellsHeuristic implements Heuristic {

	@Override
	public Integer getValue(State state) {
		FillZoneState fState = (FillZoneState) state;
		Graph graph = fState.getGraph();
		return graph.getHeight() * graph.getWidth() - graph.getCurrentCellsCount();
	}
	
	@Override
	public String toString() {
		return "Unvisited Cells Heur.";
	}

}
