package ar.edu.itba.sia.tpe.game;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {

    private EquipmentData data;
    private List<Character> sample;

    public GeneticAlgorithm() {
        Configuration.parseConfiguration();
        data = new EquipmentData();
        sample = new ArrayList<>();
    }

    public Character run() {
        initialSampling();
        int generations = 0;
        //WIP
        return null;
    }

    private void initialSampling() {
        int weaponsCount = data.weapons.size();
        int bootsCount = data.boots.size();
        int helmetsCount = data.helmets.size();
        int glovesCount = data.gloves.size();
        int breastplatesCount = data.breastplates.size();

        for (int i = 0; i < Configuration.sampleSize; i++) {
            double height = Rand.randDouble(1.3, 2.0);
            Character character = new Character(height);
            character.equipWeapon(data.weapons.get(Rand.randInt(0, weaponsCount)));
            character.equipBoots(data.boots.get(Rand.randInt(0, bootsCount)));
            character.equipHelmet(data.helmets.get(Rand.randInt(0, helmetsCount)));
            character.equipGloves(data.gloves.get(Rand.randInt(0, glovesCount)));
            character.equipBreastplate(data.breastplates.get(Rand.randInt(0, breastplatesCount)));
            sample.add(character);
        }
    }
}
