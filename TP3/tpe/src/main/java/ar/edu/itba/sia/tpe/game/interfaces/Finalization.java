package ar.edu.itba.sia.tpe.game.interfaces;

import java.util.List;
import ar.edu.itba.sia.tpe.game.Character;

public interface Finalization {
	
	public boolean isFinished(List<Character> population, int generations);

}
