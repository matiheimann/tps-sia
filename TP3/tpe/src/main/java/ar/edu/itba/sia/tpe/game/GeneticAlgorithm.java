package ar.edu.itba.sia.tpe.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneticAlgorithm {

    public GeneticAlgorithm() {
        Configuration.parseConfiguration();
        EquipmentData.parseData();
    }

    public Character run() {
        int generations = 0;
        List<Character> population = initialSampling();
        //WIP
        while (!Configuration.finalizationMethod.isFinished(population, generations)) {
            //Replacement
            population = Configuration.replacementMethod.replace(population, generations);

            generations++;
        }
        return Collections.max(population);
    }

    private List<Character> initialSampling() {
        List<Character> population = new ArrayList<>();
        int weaponsCount = EquipmentData.equipments[0].size();
        int helmetsCount = EquipmentData.equipments[1].size();
        int bootsCount = EquipmentData.equipments[2].size();
        int glovesCount = EquipmentData.equipments[3].size();
        int breastplatesCount = EquipmentData.equipments[4].size();

        for (int i = 0; i < Configuration.populationSize; i++) {
            double height = Rand.randDouble(1.3, 2.0);
            Character character = new Character(height);
            character.equipWeapon(EquipmentData.equipments[0].get(Rand.randInt(0, weaponsCount)));
            character.equipBoots(EquipmentData.equipments[1].get(Rand.randInt(0, bootsCount)));
            character.equipHelmet(EquipmentData.equipments[2].get(Rand.randInt(0, helmetsCount)));
            character.equipGloves(EquipmentData.equipments[3].get(Rand.randInt(0, glovesCount)));
            character.equipBreastplate(EquipmentData.equipments[4].get(Rand.randInt(0, breastplatesCount)));
            population.add(character);
        }
        return population;
    }


}
