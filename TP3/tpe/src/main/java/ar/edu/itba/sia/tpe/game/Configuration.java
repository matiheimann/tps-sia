package ar.edu.itba.sia.tpe.game;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    static double attackMultiplier;
    static double defenseMultiplier;

    static double strengthMultiplier;
    static double agilityMultiplier;
    static double expertiseMultiplier;
    static double resistanceMultiplier;
    static double healthMultiplier;

    static int populationSize;

    static int selectionSize;
    static SelectionMethods firstSelectionMethod;
    static SelectionMethods secondSelectionMethod;
    static double firstSelectionMethodP;
    static int tournamentsM;

    static double crossoverP;
    static CrossoverMethods crossoverMethod;

    static double mutationP;
    static MutationMethods mutationMethod;

    static ReplacementMethods replacementMethod;
    static SelectionMethods firstReplacementMethod;
    static SelectionMethods secondReplacementMethod;
    static double firstReplacementMethodP;
    
    static FinalizationMethods finalizationMethod;
    static int maxGenerations;
    static double optimalFitness;
    static double fitnessEpsilon;
    static double populationUnchangedPercentage;

    public static void parseConfiguration() {
        Properties p = new Properties();
        try(InputStream i = new FileInputStream("config.txt")) {
            p.load(i);

            attackMultiplier = Double.parseDouble(p.getProperty("ATTACK_MULTIPLIER", "0.7"));
            defenseMultiplier = Double.parseDouble(p.getProperty("DEFENSE_MULTIPLIER", "0.3"));
            strengthMultiplier = Double.parseDouble(p.getProperty("STRENGTH_MULTIPLIER", "0.8"));
            agilityMultiplier = Double.parseDouble(p.getProperty("AGILITY_MULTIPLIER", "1.2"));
            expertiseMultiplier = Double.parseDouble(p.getProperty("EXPERTISE_MULTIPLIER", "1.1"));
            resistanceMultiplier = Double.parseDouble(p.getProperty("RESISTANCE_MULTIPLIER", "1.0"));
            healthMultiplier = Double.parseDouble(p.getProperty("HEALTH_MULTIPLIER", "0.8"));
            populationSize = Integer.parseInt(p.getProperty("POPULATION_SIZE", "10"));
            selectionSize = Integer.parseInt(p.getProperty("SELECTION_SIZE", "5"));
            firstSelectionMethod = SelectionMethods.valueOf(p.getProperty("FIRST_SELECTION_METHOD", "ELITE"));
            secondSelectionMethod = SelectionMethods.valueOf(p.getProperty("SECOND_SELECTION_METHOD", "ELITE"));
            firstSelectionMethodP = Double.parseDouble(p.getProperty("FIRST_SELECTION_METHOD_PERCENTAGE", "0.8"));
            tournamentsM = Integer.parseInt(p.getProperty("TOURNAMENTS_M", "2"));
            crossoverP = Double.parseDouble(p.getProperty("CROSSOVER_PROBABILITY", "0.8"));
            crossoverMethod = CrossoverMethods.valueOf(p.getProperty("CROSSOVER_METHOD", "ONE_POINT_CROSS"));
            mutationP = Double.valueOf(p.getProperty("MUTATION_PROBABILITY", "0.5"));
            mutationMethod = MutationMethods.valueOf(p.getProperty("MUTATION_METHOD", "GEN_UNIFORM"));
            replacementMethod = ReplacementMethods.valueOf(p.getProperty("REPLACEMENT_METHOD", "METHOD_1"));
            firstReplacementMethod = SelectionMethods.valueOf(p.getProperty("FIRST_REPLACEMENT_METHOD", "ELITE"));
            secondReplacementMethod = SelectionMethods.valueOf(p.getProperty("SECOND_REPLACEMENT_METHOD", "ELITE"));
            firstReplacementMethodP = Double.parseDouble(p.getProperty("FIRST_REPLACEMENT_METHOD_PERCENTAGE", "0.8"));
            finalizationMethod = FinalizationMethods.valueOf(p.getProperty("FINALIZATION_METHOD", "MAX_GENERATIONS"));
            maxGenerations = Integer.parseInt(p.getProperty("MAX_GENERATIONS", "100"));
            optimalFitness = Double.parseDouble(p.getProperty("OPTIMAL_FITNESS", "2.5"));
            fitnessEpsilon = Double.parseDouble(p.getProperty("FITNESS_EPSILON", "0.05"));
            populationUnchangedPercentage = Double.parseDouble(p.getProperty("POPULATION_UNCHANGED_PERCENTAGE", "0.9"));
            
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
