package ar.edu.itba.sia.tpe.game;

import ar.edu.itba.sia.tpe.game.interfaces.Selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum SelectionMethods implements Selection {

    ELITE {

        @Override
        public List<Character> select(List<Character> population, int size, int generations) {
            List<Character> aux = new ArrayList<>(population);
            Collections.sort(aux, Collections.reverseOrder());
            return aux.subList(0, size);
        }

    },

    ROULETTE {

        @Override
        public List<Character> select(List<Character> population, int size, int generations) {
            double[] randomNumbers = new double[size];

            for (int i = 0; i < size; i++) {
                randomNumbers[i] = Rand.randDouble();
            }

            return cumulativeFitnessSelection(population, randomNumbers);
        }

    },

    UNIVERSAL {

        @Override
        public List<Character> select(List<Character> population, int size, int generations) {
            double[] randomNumbers = new double[size];

            double randomNumber = Rand.randDouble();
            for (int i = 0; i < size; i++) {
                randomNumbers[i] = (randomNumber + i) / size;
            }

            return cumulativeFitnessSelection(population, randomNumbers);
        }

    },

    BOLTZMANN_ROULETTE {

        @Override
        public List<Character> select(List<Character> population, int size, int generations) {
            double[] randomNumbers = new double[size];

            for (int i = 0; i < size; i++) {
                randomNumbers[i] = Rand.randDouble();
            }

            double temperature = 100.0 / (generations + 1);
            return boltzmannSelection(population, randomNumbers, temperature);
        }

    },

    DETERMINISTIC_TOURNAMENTS {

        @Override
        public List<Character> select(List<Character> population, int size, int generations) {
            List<Character> selection = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Character winner = null;
                for (int j = 0; j < Configuration.tournamentsM; j++) {
                    Character character = population.get(Rand.randInt(population.size()));
                    if (winner == null || character.getFitness() > winner.getFitness()) {
                        winner = character;
                    }
                }
                selection.add(winner);
            }
            return selection;
        }

    },

    PROBABILISTIC_TOURNAMENTS {

        @Override
        public List<Character> select(List<Character> population, int size, int generations) {
            List<Character> selection = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Character character1 = population.get(Rand.randInt(population.size()));
                Character character2 = population.get(Rand.randInt(population.size()));
                boolean bestFitness = Rand.randDouble() < 0.75;
                if (bestFitness) {
                    if (character1.getFitness() > character2.getFitness()) {
                        selection.add(character1);
                    } else {
                        selection.add(character2);
                    }
                } else {
                    if (character1.getFitness() < character2.getFitness()) {
                        selection.add(character1);
                    } else {
                        selection.add(character2);
                    }
                }
            }
            return selection;
        }

    },

    RANKING {

        @Override
        public List<Character> select(List<Character> population, int size, int generations) {
            double[] randomNumbers = new double[size];

            for (int i = 0; i < size; i++) {
                randomNumbers[i] = Rand.randDouble();
            }

            return rankingSelection(population, randomNumbers);
        }

    };

    public static List<Character> selectWrapperA(List<Character> population, int size, int generations) {
        List<Character> selection = new ArrayList<>();
        int size1 = (int)Math.round(size * Configuration.firstSelectionMethodP);
        int size2 = size - size1;
        selection.addAll(Configuration.firstSelectionMethod.select(population, size1, generations));
        selection.addAll(Configuration.secondSelectionMethod.select(population, size2, generations));
        return selection;
    }

    public static List<Character> selectWrapperB(List<Character> population, int size, int generations) {
        List<Character> selection = new ArrayList<>();
        int size1 = (int)Math.round(size * Configuration.firstReplacementMethodP);
        int size2 = size - size1;
        selection.addAll(Configuration.firstReplacementMethod.select(population, size1, generations));
        selection.addAll(Configuration.secondReplacementMethod.select(population, size2, generations));
        return selection;
    }

    private static List<Character> cumulativeFitnessSelection(List<Character> population, double[] randomNumbers) {
        double[] cumulativeFitness = new double[population.size()];
        for (int i = 0; i < population.size(); i++) {
            if (i == 0) {
                cumulativeFitness[i] = population.get(i).getFitness();
            } else {
                cumulativeFitness[i] = cumulativeFitness[i - 1] + population.get(i).getFitness();
            }
        }
        for (int i = 0; i < population.size(); i++) {
            cumulativeFitness[i] /= cumulativeFitness[population.size() - 1];
        }
        return randomSelection(population, cumulativeFitness, randomNumbers);
    }

    private static List<Character> boltzmannSelection(List<Character> population, double[] randomNumbers, double temperature) {
        double average = averageBoltzmannValue(population, temperature);
        double[] cumulativeProbabilities = new double[population.size()];
        for (int i = 0; i < population.size(); i++) {
            if (i == 0) {
                cumulativeProbabilities[i] = Math.exp(population.get(i).getFitness() / temperature) / average;
            } else {
                cumulativeProbabilities[i] = cumulativeProbabilities[i - 1] + Math.exp(population.get(i).getFitness() / temperature) / average;
            }
        }
        for (int i = 0; i < population.size(); i++) {
            cumulativeProbabilities[i] /= cumulativeProbabilities[population.size() - 1];
        }
        return randomSelection(population, cumulativeProbabilities, randomNumbers);
    }

    private static List<Character> rankingSelection(List<Character> population, double[] randomNumbers) {
        double[] cumulativeProbabilities = new double[population.size()];
        List<Character> aux = new ArrayList<>(population);
        Collections.sort(aux);
        for (int i = 0; i < population.size(); i++) {
            if (i == 0) {
                cumulativeProbabilities[i] = 1.0;
            } else {
                cumulativeProbabilities[i] = cumulativeProbabilities[i - 1] + i + 1.0;
            }
        }
        for (int i = 0; i < population.size(); i++) {
            cumulativeProbabilities[i] /= cumulativeProbabilities[population.size() - 1];
        }
        return randomSelection(aux, cumulativeProbabilities, randomNumbers);
    }

    private static List<Character> randomSelection(List<Character> population, double[] cumulativeProbabilities, double[] randomNumbers) {
        List<Character> selection = new ArrayList<>();
        for(int i = 0; i < randomNumbers.length; i++) {
            for (int j = 0; j < population.size(); j++) {
                if (cumulativeProbabilities[j] > randomNumbers[i]) {
                    selection.add(population.get(j));
                    break;
                }
            }
        }
        return selection;
    }

    private static double averageBoltzmannValue(List<Character> population, double temperature) {
        double sum = 0.0;
        for (Character c : population) {
            sum += Math.exp(c.getFitness() / temperature);
        }
        return sum / population.size();
    }

}
