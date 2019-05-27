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

    static int sampleSize;

    static int tournamentsM;

    public static void parseConfiguration() {
        Properties p = new Properties();
        try {
            InputStream i = new FileInputStream("config.txt");
            p.load(i);

            attackMultiplier = Double.parseDouble(p.getProperty("ATTACK_MULTIPLIER", "0.7"));
            defenseMultiplier = Double.parseDouble(p.getProperty("DEFENSE_MULTIPLIER", "0.3"));
            strengthMultiplier = Double.parseDouble(p.getProperty("STRENGTH_MULTIPLIER", "0.8"));
            agilityMultiplier = Double.parseDouble(p.getProperty("AGILITY_MULTIPLIER", "1.2"));
            expertiseMultiplier = Double.parseDouble(p.getProperty("EXPERTISE_MULTIPLIER", "1.1"));
            resistanceMultiplier = Double.parseDouble(p.getProperty("RESISTANCE_MULTIPLIER", "1.0"));
            healthMultiplier = Double.parseDouble(p.getProperty("HEALTH_MULTIPLIER", "0.8"));
            sampleSize = Integer.parseInt(p.getProperty("SAMPLE_SIZE", "10"));
            tournamentsM = Integer.parseInt(p.getProperty("TOURNAMENTS_M", "2"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
