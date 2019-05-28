package ar.edu.itba.sia.tpe.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneticAlgorithm {

    public GeneticAlgorithm() {
        Configuration.parseConfiguration();
        EquipmentData.parseData();
        System.out.println();
    }

    public Character run() {
        int generations = 0;
        List<Character> population = initialSampling();
        System.out.println("Generation: " + generations);
        System.out.println(Collections.max(population));
        System.out.println();

        while (!Configuration.finalizationMethod.isFinished(population, generations)) {
            //Replacement
            population = Configuration.replacementMethod.replace(population, generations);
            generations++;

            System.out.println("Generation: " + generations);
            System.out.println(Collections.max(population));
            System.out.println();
        }
        return Collections.max(population);
    }

    private List<Character> initialSampling() {
        List<Character> population = new ArrayList<>();
        int weaponsCount = EquipmentData.equipments[0].size();
        int bootsCount = EquipmentData.equipments[1].size();
        int helmetsCount = EquipmentData.equipments[2].size();
        int glovesCount = EquipmentData.equipments[3].size();
        int breastplatesCount = EquipmentData.equipments[4].size();

        for (int i = 0; i < Configuration.populationSize; i++) {
            double height = Rand.randDouble(1.3, 2.0);
            Character character = new Character(height);
            character.equipWeapon(EquipmentData.equipments[0].get(Rand.randInt(weaponsCount - 1)));
            character.equipBoots(EquipmentData.equipments[1].get(Rand.randInt(bootsCount - 1)));
            character.equipHelmet(EquipmentData.equipments[2].get(Rand.randInt(helmetsCount - 1)));
            character.equipGloves(EquipmentData.equipments[3].get(Rand.randInt(glovesCount - 1)));
            character.equipBreastplate(EquipmentData.equipments[4].get(Rand.randInt(breastplatesCount - 1)));
            population.add(character);
        }
        return population;
    }


}
