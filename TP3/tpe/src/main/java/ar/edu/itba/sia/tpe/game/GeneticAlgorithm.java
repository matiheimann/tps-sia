package ar.edu.itba.sia.tpe.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneticAlgorithm {

    private EquipmentData data;

    public GeneticAlgorithm() {
        Configuration.parseConfiguration();
        data = new EquipmentData();
    }

    public Character run() {
        int generations = 0;
        List<Character> population = initialSampling();
        //WIP
        while (!isFinished(population, generations)) {
            //Replacement
            population = Configuration.replacementMethod.replace(population, generations);

            generations++;
        }
        return Collections.max(population);
    }

    private List<Character> initialSampling() {
        List<Character> population = new ArrayList<>();
        int weaponsCount = data.weapons.size();
        int bootsCount = data.boots.size();
        int helmetsCount = data.helmets.size();
        int glovesCount = data.gloves.size();
        int breastplatesCount = data.breastplates.size();

        for (int i = 0; i < Configuration.populationSize; i++) {
            double height = Rand.randDouble(1.3, 2.0);
            Character character = new Character(height);
            character.equipWeapon(data.weapons.get(Rand.randInt(0, weaponsCount)));
            character.equipBoots(data.boots.get(Rand.randInt(0, bootsCount)));
            character.equipHelmet(data.helmets.get(Rand.randInt(0, helmetsCount)));
            character.equipGloves(data.gloves.get(Rand.randInt(0, glovesCount)));
            character.equipBreastplate(data.breastplates.get(Rand.randInt(0, breastplatesCount)));
            population.add(character);
        }
        return population;
    }

    private boolean isFinished(List<Character> population, int generations) {
        return false;
    }


}
