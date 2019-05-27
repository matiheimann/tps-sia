package ar.edu.itba.sia.tpe.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum SelectionMethods implements Selection {

    ELITE {

        @Override
        public List<Character> select(List<Character> sample, int size, int generations) {
            List<Character> aux = new ArrayList<>(sample);
            Collections.sort(aux, Collections.reverseOrder());
            return aux.subList(0, size);
        }

    },

    ROULETTE {

        @Override
        public List<Character> select(List<Character> sample, int size, int generations) {
            double[] randomNumbers = new double[size];

            for (int i = 0; i < size; i++) {
                randomNumbers[i] = Rand.randDouble();
            }

            return cumulativeFitnessSelection(sample, randomNumbers);
        }

    },

    UNIVERSAL {

        @Override
        public List<Character> select(List<Character> sample, int size, int generations) {
            double[] randomNumbers = new double[size];

            double randomNumber = Rand.randDouble();
            for (int i = 0; i < size; i++) {
                randomNumbers[i] = (randomNumber + i) / size;
            }

            return cumulativeFitnessSelection(sample, randomNumbers);
        }

    },

    BOLTZMANN_ROULETTE {

        @Override
        public List<Character> select(List<Character> sample, int size, int generations) {
            double[] randomNumbers = new double[size];

            for (int i = 0; i < size; i++) {
                randomNumbers[i] = Rand.randDouble();
            }

            double temperature = 100.0 / (generations + 1);
            return boltzmannSelection(sample, randomNumbers, temperature);
        }

    },

    DETERMINISTIC_TOURNAMENTS {

        @Override
        public List<Character> select(List<Character> sample, int size, int generations) {
            List<Character> selection = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Character winner = null;
                for (int j = 0; j < Configuration.tournamentsM; j++) {
                    Character character = sample.get(Rand.randInt(sample.size()));
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
        public List<Character> select(List<Character> sample, int size, int generations) {
            List<Character> selection = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Character character1 = sample.get(Rand.randInt(sample.size()));
                Character character2 = sample.get(Rand.randInt(sample.size()));
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
        public List<Character> select(List<Character> sample, int size, int generations) {
            double[] randomNumbers = new double[size];

            for (int i = 0; i < size; i++) {
                randomNumbers[i] = Rand.randDouble();
            }

            return rankingSelection(sample, randomNumbers);
        }

    };

    private static List<Character> cumulativeFitnessSelection(List<Character> sample, double[] randomNumbers) {
        double[] cumulativeFitness = new double[sample.size()];
        for (int i = 0; i < sample.size(); i++) {
            if (i == 0) {
                cumulativeFitness[i] = sample.get(i).getFitness();
            } else {
                cumulativeFitness[i] = cumulativeFitness[i - 1] + sample.get(i).getFitness();
            }
        }
        for (int i = 0; i < sample.size(); i++) {
            cumulativeFitness[i] /= cumulativeFitness[sample.size() - 1];
        }
        return randomSelection(sample, cumulativeFitness, randomNumbers);
    }

    private static List<Character> boltzmannSelection(List<Character> sample, double[] randomNumbers, double temperature) {
        double average = averageBoltzmannValue(sample, temperature);
        double[] cumulativeProbabilities = new double[sample.size()];
        for (int i = 0; i < sample.size(); i++) {
            if (i == 0) {
                cumulativeProbabilities[i] = Math.exp(sample.get(i).getFitness() / temperature) / average;
            } else {
                cumulativeProbabilities[i] = cumulativeProbabilities[i - 1] + Math.exp(sample.get(i).getFitness() / temperature) / average;
            }
        }
        for (int i = 0; i < sample.size(); i++) {
            cumulativeProbabilities[i] /= cumulativeProbabilities[sample.size() - 1];
        }
        return randomSelection(sample, cumulativeProbabilities, randomNumbers);
    }

    private static List<Character> rankingSelection(List<Character> sample, double[] randomNumbers) {
        double[] cumulativeProbabilities = new double[sample.size()];
        List<Character> aux = new ArrayList<>(sample);
        Collections.sort(aux);
        for (int i = 0; i < sample.size(); i++) {
            if (i == 0) {
                cumulativeProbabilities[i] = 1.0;
            } else {
                cumulativeProbabilities[i] = cumulativeProbabilities[i - 1] + i + 1.0;
            }
        }
        for (int i = 0; i < sample.size(); i++) {
            cumulativeProbabilities[i] /= cumulativeProbabilities[sample.size() - 1];
        }
        return randomSelection(aux, cumulativeProbabilities, randomNumbers);
    }

    private static List<Character> randomSelection(List<Character> sample, double[] cumulativeProbabilities, double[] randomNumbers) {
        List<Character> selection = new ArrayList<>();
        for(int i = 0; i < randomNumbers.length; i++) {
            for (int j = 0; j < sample.size(); j++) {
                if (cumulativeProbabilities[j] > randomNumbers[i]) {
                    selection.add(sample.get(j));
                    break;
                }
            }
        }
        return selection;
    }

    private static double averageBoltzmannValue(List<Character> sample, double temperature) {
        double sum = 0.0;
        for (Character c : sample) {
            sum += Math.exp(c.getFitness() / temperature);
        }
        return sum / sample.size();
    }

}
