package ar.edu.itba.sia.tpe.game.interfaces;

import ar.edu.itba.sia.tpe.game.Character;

import java.util.List;

public interface Selection {

    List<Character> select(List<Character> population, int size, int generations);

}
