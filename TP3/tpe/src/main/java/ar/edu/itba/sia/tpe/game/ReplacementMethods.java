package ar.edu.itba.sia.tpe.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ar.edu.itba.sia.tpe.game.interfaces.Replacement;

public enum ReplacementMethods implements Replacement {

    METHOD_1 {

        @Override
        public List<Character> replace(List<Character> population, int generations) {
            List<Character> children = generateChildren(population, population.size(), generations);
            return children;
        }

    },

    METHOD_2 {

        @Override
        public List<Character> replace(List<Character> population, int generations) {
            List<Character> newPopulation = new ArrayList<>();
            List<Character> children = generateChildren(population, Configuration.selectionSize, generations);

            newPopulation.addAll(children);

            //Selection B
            List<Character> selection = SelectionMethods.selectWrapperB(population, population.size() - Configuration.selectionSize, generations);
            newPopulation.addAll(selection);

            return newPopulation;
        }

    },

    METHOD_3 {

        @Override
        public List<Character> replace(List<Character> population, int generations) {
            List<Character> newPopulation = new ArrayList<>();
            List<Character> children = generateChildren(population, Configuration.selectionSize, generations);

            //Selection B
            List<Character> selection = SelectionMethods.selectWrapperB(population, population.size() - Configuration.selectionSize, generations);
            newPopulation.addAll(selection);

            //Selection B
            List<Character> totalPopulation = new ArrayList<>();
            totalPopulation.addAll(population);
            totalPopulation.addAll(children);
            selection = SelectionMethods.selectWrapperB(totalPopulation, Configuration.selectionSize, generations);
            newPopulation.addAll(selection);

            return newPopulation;
        }

    };

    private static List<Character> generateChildren(List<Character> population, int size, int generations) {
        List<Character> children = new ArrayList<>();

        //Selection A
        List<Character> selection = SelectionMethods.selectWrapperA(population, size, generations);

        //Crossover
        Collections.shuffle(selection);
        for (int i = 0; i < selection.size() / 2; i++) {
            Character[] crossover = CrossoverMethods.crossoverWrapper(selection.get(2 * i), selection.get((2 * i) + 1));
            children.add(crossover[0]);
            children.add(crossover[1]);
        }
        if (children.size() < selection.size()) {
            children.add(selection.get(Rand.randInt(selection.size() - 1)));
        }

        //Mutate
        for (Character child : children) {
            MutationMethods.mutateWrapper(child, generations);
        }

        return children;
    }

}
