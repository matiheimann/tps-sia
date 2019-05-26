package ar.edu.itba.sia.tpe.game;

import java.util.concurrent.ThreadLocalRandom;

public class Rand {

    public static double randDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    public static int randInt(int max) {
        return ThreadLocalRandom.current().nextInt(max + 1);
    }

    public static double randDouble(double max) {
        return ThreadLocalRandom.current().nextDouble(max);
    }

    public static int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static double randDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

}
