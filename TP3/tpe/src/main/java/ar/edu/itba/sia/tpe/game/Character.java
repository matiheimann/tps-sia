package ar.edu.itba.sia.tpe.game;

public abstract class Character {

    private double height;
    private Equipment[] equipment;

    public Character(double height, Equipment weapon, Equipment helmet, Equipment boots,
                     Equipment gloves, Equipment shirtfront){
        this.height = height;
        this.equipment = new Equipment[5];
        this.equipment[0] = weapon;
        this.equipment[1] = helmet;
        this.equipment[2] = boots;
        this.equipment[3] = gloves;
        this.equipment[4] = shirtfront;
    }

    public Character(Character c){
        this.height = c.getHeight();
        this.equipment = new Equipment[5];
        this.equipment[0] = c.equipment[0];
        this.equipment[1] = c.equipment[1];
        this.equipment[2] = c.equipment[2];
        this.equipment[3] = c.equipment[3];
        this.equipment[4] = c.equipment[4];
    }

    public void equipElement(Equipment e, int idx){
        this.equipment[idx] = e;
    }

    public void equipWeapon(Equipment weapon){
        this.equipment[0] = weapon;
    }

    public void equipHelmet(Equipment helmet){
        this.equipment[1] = helmet;
    }

    public void equipBoots(Equipment boots){
        this.equipment[2] = boots;
    }

    public void equipGloves(Equipment gloves){
        this.equipment[3] = gloves;
    }

    public void equipShirtFront(Equipment shirtfront){
        this.equipment[4] = shirtfront;
    }

    public double getHeight(){
        return this.height;
    }

    public Equipment[] getEquipment(){
        return this.equipment;
    }

    public double getAttack(){
        double strength = 0.0;
        double agility = 0.0;
        double expertise = 0.0;
        if(this.equipment[0] != null) {
            strength += this.equipment[0].getStrenght();
            agility += this.equipment[0].getExpertise();
            expertise += this.equipment[0].getExpertise();
        }
        if(this.equipment[1] != null) {
            strength += equipment[1].getStrenght();
            agility += equipment[1].getExpertise();
            expertise += equipment[1].getAgility();
        }
        if(this.equipment[2] != null){
            strength += equipment[2].getStrenght();
            agility += equipment[2].getAgility();
            expertise += equipment[2].getExpertise();
        }
        if(this.equipment[3] != null){
            strength += equipment[3].getStrenght();
            agility += equipment[3].getAgility();
            expertise += equipment[3].getExpertise();
        }
        if(this.equipment[4] != null){
            strength += equipment[4].getStrenght();
            agility += equipment[4].getAgility();
            expertise += equipment[4].getExpertise();
        }
        double strenghtP = 100 * Math.tanh(0.01 * strength);
        double agilityP = Math.tanh(0.01 * agility);
        double expertiseP = 0.6 * Math.tanh(0.01 * expertise);
        double atm = 0.5 - Math.pow(3*height - 5, 4) + Math.pow(3*height - 5, 2) + height/2;
        return (agilityP + expertiseP) * strenghtP * atm;
    }

    public double getDefense(){
        double resistance = 0.0;
        double expertise = 0.0;
        double hp = 0.0;
        if(this.equipment[0] != null) {
            resistance += equipment[0].getResistance();
            hp += equipment[0].getHealthPoints();
            expertise += equipment[0].getExpertise();
        }
        if(this.equipment[1] != null) {
            resistance += equipment[1].getResistance();
            hp += equipment[1].getHealthPoints();
            expertise += equipment[1].getExpertise();
        }
        if(this.equipment[2] != null){
            resistance += equipment[2].getResistance();
            hp += equipment[2].getHealthPoints();
            expertise += equipment[2].getExpertise();
        }
        if(this.equipment[3] != null){
            resistance += equipment[3].getResistance();
            hp += equipment[3].getHealthPoints();
            expertise += equipment[3].getExpertise();
        }
        if(this.equipment[3] != null){
            resistance += equipment[3].getResistance();
            hp += equipment[3].getHealthPoints();
            expertise += equipment[3].getExpertise();
        }
        double expertiseP = 0.6 * Math.tanh(0.01 * expertise);
        double resistanceP = Math.tanh(0.01 * resistance);
        double hpP = 100 * Math.tanh(0.01 * hp);
        double dem = 2 + Math.pow(3 * height - 5, 4) + Math.pow(3 * height - 5, 2) - height/2;
        return (resistanceP + expertiseP) * hpP * dem;
    }

    public abstract double getPerformance();

}
