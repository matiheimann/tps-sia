package ar.edu.itba.sia.tpe.game;

import java.lang.reflect.Array;
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
        int weaponsCount = EquipmentData.getEquipments()[0].size();
        int helmetsCount = EquipmentData.getEquipments()[1].size();
        int bootsCount = EquipmentData.getEquipments()[2].size();
        int glovesCount = EquipmentData.getEquipments()[3].size();
        int breastplatesCount = EquipmentData.getEquipments()[4].size();

        for (int i = 0; i < Configuration.populationSize; i++) {
            double height = Rand.randDouble(1.3, 2.0);
            Character character = new Character(height);
            character.equipWeapon(EquipmentData.getEquipments()[0].get(Rand.randInt(0, weaponsCount)));
            character.equipBoots(EquipmentData.getEquipments()[1].get(Rand.randInt(0, bootsCount)));
            character.equipHelmet(EquipmentData.getEquipments()[2].get(Rand.randInt(0, helmetsCount)));
            character.equipGloves(EquipmentData.getEquipments()[3].get(Rand.randInt(0, glovesCount)));
            character.equipBreastplate(EquipmentData.getEquipments()[4].get(Rand.randInt(0, breastplatesCount)));
            population.add(character);
        }
        return population;
    }

    private boolean isFinished(List<Character> population, int generations) {
        return false;
    }


}
