package ar.edu.itba.sia.tpe.game;

public class Equipment {

    private int id;
    private double strength;
    private double agility;
    private double expertise;
    private double resistance;
    private double health;

    public Equipment(int id, double s, double a, double e, double r, double h) {
        this.id = id;
        this.strength = s;
        this.agility = a;
        this.expertise = e;
        this.resistance = r;
        this.health = h;
    }

    public int getId() { return id; }

    public double getStrength() { return strength; }

    public double getAgility() { return agility; }

    public double getExpertise() { return expertise; }

    public double getResistance() { return resistance; }

    public double getHealth() { return health; }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
