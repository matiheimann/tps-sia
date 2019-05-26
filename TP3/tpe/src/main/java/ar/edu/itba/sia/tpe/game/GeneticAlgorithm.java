package ar.edu.itba.sia.tpe.game;

import java.util.List;

public class GeneticAlgorithm {

    private EquipmentData data;
    private Configuration config;

    private List<Character> sample;

    public GeneticAlgorithm() {
        data = new EquipmentData();
        config = new Configuration();
    }

    public Character run() {
        initialSampling();
        //WIP
        return null;
    }

    private void initialSampling() {
        int weaponsCount = data.weapons.size();
        int bootsCount = data.boots.size();
        int helmetsCount = data.helmets.size();
        int glovesCount = data.gloves.size();
        int breastplatesCount = data.breastplates.size();

        for (int i = 0; i < config.sampleSize; i++) {
            double height = Rand.randDouble(1.3, 2.0);
            Character character = new Character(height, config.attackMultiplier, config.defenseMultiplier,
                    config.strengthMultiplier, config.agilityMultiplier, config.expertiseMultiplier,
                    config.resistanceMultiplier, config.healthMultiplier);
            character.equipWeapon(data.weapons.get(Rand.randInt(0, weaponsCount)));
            character.equipBoots(data.boots.get(Rand.randInt(0, bootsCount)));
            character.equipHelmet(data.helmets.get(Rand.randInt(0, helmetsCount)));
            character.equipGloves(data.gloves.get(Rand.randInt(0, glovesCount)));
            character.equipBreastplate(data.breastplates.get(Rand.randInt(0, breastplatesCount)));
            sample.add(character);
        }
    }
}
