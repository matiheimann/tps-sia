package ar.edu.itba.sia.gps.fillzone;

import java.util.List;

import ar.edu.itba.sia.gps.api.Problem;
import ar.edu.itba.sia.gps.api.Rule;
import ar.edu.itba.sia.gps.api.State;

public class FillZoneProblem implements Problem {

	@Override
	public State getInitState() {
		return null;
	}

	@Override
	public boolean isGoal(State state) {
		FillZoneState fzs = (FillZoneState) state;
		Color[][] b = fzs.getBoard();
		Color c = b[0][0];
		
		for(int i = 0; i < b.length; i++)
			for(int j = 0; j < b[0].length; j++)
				if(b[i][j].getValue() != c.getValue())
					return false;
		
		return true;
	}

	@Override
	public List<Rule> getRules() {
		// TODO Auto-generated method stub
		return null;
	}

}
