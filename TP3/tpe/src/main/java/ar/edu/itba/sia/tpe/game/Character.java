package ar.edu.itba.sia.tpe.game;

import java.util.Arrays;
import java.util.Objects;

public class Character implements Comparable<Character> {

    private static int WEAPON = 0;
    private static int HELMET = 1;
    private static int BOOTS = 2;
    private static int GLOVES = 3;
    private static int BREASTPLATE = 4;
    private static int EQUIPMENT_SIZE = 5;

    private double height;
    private Equipment[] equipment = new Equipment[EQUIPMENT_SIZE];

    public Character(double height) {
        this.height = height;
    }

    @Override
    public Character clone() {
        Character c = new Character(height);
        int i = 0;
        for (Equipment e : c.equipment) {
            equipElement(e, i++);
        }
        return c;
    }

    @Override
    public int compareTo(Character o) {
        return Double.compare(getFitness(), o.getFitness());
    }

    public double getFitness() {
        return Configuration.attackMultiplier * getAttack() + Configuration.defenseMultiplier * getDefense();
    }

    public double getHeight(){
        return this.height;
    }

    public double getAttack() {
        return (getAgility() + getExpertise()) * getStrength() * getATM();
    }

    public double getDefense() {
        return (getResistance() + getExpertise()) * getHealth() * getDEM();
    }

    public double getStrength() {
        double strength = 0;
        for (Equipment e : equipment) {
            strength += e.getStrength();
        }
        return 100 * Math.tanh(0.01 * strength * Configuration.strengthMultiplier);
    }

    public double getAgility() {
        double agility = 0;
        for (Equipment e : equipment) {
            agility += e.getAgility();
        }
        return Math.tanh(0.01 * agility * Configuration.agilityMultiplier);
    }

    public double getExpertise() {
        double expertise = 0;
        for (Equipment e : equipment) {
            expertise += e.getExpertise();
        }
        return 0.6 * Math.tanh(0.01 * expertise * Configuration.expertiseMultiplier);
    }

    public double getResistance() {
        double resistance = 0;
        for (Equipment e : equipment) {
            resistance += e.getResistance();
        }
        return Math.tanh(0.01 * resistance * Configuration.resistanceMultiplier);
    }

    public double getHealth() {
        double health = 0;
        for (Equipment e : equipment) {
            health += e.getHealth();
        }
        return 100 * Math.tanh(0.01 * health * Configuration.healthMultiplier);
    }

    public double getATM() {
        return 0.5 - Math.pow(3 * height - 5, 4) + Math.pow(3 * height - 5, 2) + height / 2;
    }

    public double getDEM() {
        return 2 + Math.pow(3 * height - 5, 4) - Math.pow(3 * height - 5, 2) - height / 2;
    }

    public Equipment[] getEquipment() {
        return this.equipment;
    }

    public void setHeight(double height) {this.height = height;}

    public void equipElement(Equipment e, int idx) { this.equipment[idx] = e; }

    public void equipWeapon(Equipment weapon) { this.equipment[WEAPON] = weapon; }

    public void equipHelmet(Equipment helmet) {  this.equipment[HELMET] = helmet; }

    public void equipBoots(Equipment boots) { this.equipment[BOOTS] = boots; }

    public void equipGloves(Equipment gloves) { this.equipment[GLOVES] = gloves; }

    public void equipBreastplate(Equipment breastplate) { this.equipment[BREASTPLATE] = breastplate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return Double.compare(character.height, height) == 0 &&
                Arrays.equals(equipment, character.equipment);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(height);
        result = 31 * result + Arrays.hashCode(equipment);
        return result;
    }
}
