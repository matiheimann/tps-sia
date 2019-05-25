package ar.edu.itba.sia.tpe.game;


public class Assasin extends Character{

    public Assasin(double height, Equipment weapon, Equipment helmet, Equipment boots,
                   Equipment gloves, Equipment shirtfront){
        super(height, weapon, helmet, boots, gloves, shirtfront);
    }

    public Assasin(Character c){
        super(c);
    }

    @Override
    public double getPerformance() {
        return 0.7 * getAttack() + 0.3 * getDefense();
    }
}
