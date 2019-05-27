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
        	return Math.abs(fitness - optFitness) <= Configuration.fitnessEpsilon;
        }

    },

    POPULATION_UNCHANGED {
    	
    	private List<Character> lastPopulation = Collections.emptyList();
    	private int iterationsWithoutSignificantChange = 0;

        @Override
        public boolean isFinished(List<Character> population, int generations) {
        	double percentageChanged = compareToLastPopulation(population);
        	int maxIterations = Configuration.maxIterationsWithoutImprovement;
        	if(percentageChanged < (1 - Configuration.populationUnchangedPercentage)) {
        		if(iterationsWithoutSignificantChange > maxIterations)
        			return true;
        		iterationsWithoutSignificantChange++;
        	} else
        		iterationsWithoutSignificantChange = 0;
        	lastPopulation = population;
            return false;
        }
        
        private double compareToLastPopulation(List<Character> newPopulation) {
        	// removes from old elements those that are still present
        	double sizeBeforeChange = lastPopulation.size();
        	boolean hasChanged = lastPopulation.retainAll(newPopulation);
        	
        	if(!hasChanged)
        		return 1;
        	return lastPopulation.size() / sizeBeforeChange;
        }

    },
    
    FITNESS_MAX_REACHED {
    	
    	private int iterationsWithoutImprovement = 0;
    	private double bestFitnessYet = 0;
    	
    	@Override
    	public boolean isFinished(List<Character> population, int generations) {
    		int maxIterations = Configuration.maxIterationsWithoutImprovement;
    		if(iterationsWithoutImprovement > maxIterations)
    			return true;
    		double currentFitness = Collections.max(population).getFitness();
    		if(currentFitness > bestFitnessYet)
    			iterationsWithoutImprovement = 0;
    		else
    			iterationsWithoutImprovement++;
    		return false;
    	}

    };

}
