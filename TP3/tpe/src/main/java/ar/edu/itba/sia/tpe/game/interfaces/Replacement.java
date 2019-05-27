package ar.edu.itba.sia.tpe.game.interfaces;

import ar.edu.itba.sia.tpe.game.Character;

import java.util.List;

public interface Replacement {

    List<Character> replace(List<Character> population, int generations);

}
