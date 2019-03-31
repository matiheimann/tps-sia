package ar.edu.itba.sia.gps.fillzone;

import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.api.State;

public class MaxDistanceHeuristic implements Heuristic {

    @Override
    public Integer getValue(State state) {
    	Graph g = ((FillZoneState) state).getGraph();
        return g.getMaxDistance();
    }

}
