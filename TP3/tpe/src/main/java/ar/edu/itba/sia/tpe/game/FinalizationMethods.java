package ar.edu.itba.sia.tpe.game;

import java.util.Collections;
import java.util.List;

import ar.edu.itba.sia.tpe.game.interfaces.Finalization;

public enum FinalizationMethods implements Finalization {

	
	MAX_GENERATIONS {

        @Override
        public boolean isFinished(List<Character> population, int generations) {
        	return generations > Configuration.maxGenerations;
        }

    },

    OPTIMAL_FITNESS {

        @Override
        public boolean isFinished(List<Character> population, int generations) {
        	double fitness = Collections.max(population).getFitness();
        	double optFitness = Configuration.optimalFitness;
        	return Math.abs(fitness - optFitness) <= Configuration.fitnessEpsilon);
        }

    },

    POPULATION_UNCHANGED {

        @Override
        public boolean isFinished(List<Character> population, int generations) {
            return false;
        }

    },
    
    FITNESS_MAX_REACHED {
    	
    	@Override
    	public boolean isFinished(List<Character> population, int generations) {
    		return false;
    	}
    };

}
