package ar.edu.itba.sia.tpe.game.interfaces;

import java.util.List;
import ar.edu.itba.sia.tpe.game.Character;

public interface Finalization {
	
	boolean isFinished(List<Character> population, int generations);

}
