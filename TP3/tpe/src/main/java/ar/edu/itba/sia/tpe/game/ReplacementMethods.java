package ar.edu.itba.sia.tpe.game;

import ar.edu.itba.sia.tpe.game.interfaces.Replacement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum ReplacementMethods implements Replacement {

    METHOD_1 {

        @Override
        public List<Character> replace(List<Character> population, int generations) {
            List<Character> children = new ArrayList<>();

            while (children.size() != population.size()) {
                //Selection
                List<Character> selection = SelectionMethods.selectWrapperA(population, 2, generations);

                //Crossover
                Character[] crossover = CrossoverMethods.crossoverWrapper(selection.get(0), selection.get(1));

                //Mutation
                //MutationMethods.mutateWrapper(crossover[0]);
                //MutationMethods.mutateWrapper(crossover[1];

                children.add(crossover[0]);
                children.add(crossover[1]);
            }

            return children;
        }

    },

    METHOD_2 {

        @Override
        public List<Character> replace(List<Character> population, int generations) {
            List<Character> children = new ArrayList<>();

            //Selection
            List<Character> selection = SelectionMethods.selectWrapperA(population, Configuration.selectionSize, generations);

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
                //MutationMethods.mutateWrapper(child);
            }

            //Selection
            children.addAll(SelectionMethods.selectWrapperB(population, population.size() - children.size(), generations));

            return children;
        }

    },

    METHOD_3 {

        @Override
        public List<Character> replace(List<Character> population, int generations) {
            return null;
        }

    };

}
