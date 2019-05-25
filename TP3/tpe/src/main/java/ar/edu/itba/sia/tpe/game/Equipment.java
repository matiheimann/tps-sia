package ar.edu.itba.sia.tpe.game;

public class Equipment {

    private double strenght;
    private double agility;
    private double expertise;
    private double resistance;
    private double healthPoints;

    public Equipment(double s, double a, double e, double r, double h){
        this.strenght = s;
        this.agility = a;
        this.expertise = e;
        this.resistance = r;
        this.healthPoints = h;
    }

    public double getStrenght() {
        return strenght;
    }

    public double getAgility() {
        return agility;
    }

    public double getExpertise() {
        return expertise;
    }

    public double getResistance() {
        return resistance;
    }

    public double getHealthPoints() {
        return healthPoints;
    }
}
