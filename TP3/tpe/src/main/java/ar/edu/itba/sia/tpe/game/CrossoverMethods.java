package ar.edu.itba.sia.tpe.game;

import ar.edu.itba.sia.tpe.game.interfaces.Crossover;
import sun.security.krb5.Config;

public enum CrossoverMethods implements Crossover {

    ONE_POINT_CROSS {

        private int geneChange = 0;

        public void setGeneChange(int geneChange) {
            if (geneChange > 4) {
                throw new RuntimeException("Not valid gene");
            }
            this.geneChange = geneChange;
        }

        @Override
        public Character[] crossover(Character c1, Character c2) {
            Character[] characters = {c1.clone(), c2.clone()};
            characters[0].equipElement(c2.getEquipment()[geneChange], geneChange);
            characters[1].equipElement(c1.getEquipment()[geneChange], geneChange);
            return characters;
        }
    },

    TWO_POINT_CROSS {

        private int firstPoint = 0;
        private int secondPoint = 1;

        public void setGeneChanges(int geneChanges1, int geneChanges2) {
            if(geneChanges1 >= geneChanges2 && geneChanges2 > 4){
                throw new RuntimeException("Not valid gene");
            }
            this.firstPoint = geneChanges1;
            this.secondPoint = geneChanges2;
        }

        @Override
        public Character[] crossover(Character c1, Character c2) {
            Character[] characters = {c1.clone(), c2.clone()};
            for(int i = firstPoint; i <= secondPoint; i++) {
                characters[0].equipElement(c2.getEquipment()[i], i);
                characters[1].equipElement(c1.getEquipment()[i], i);
            }
            return characters;
        }


    },

    ANULAR {

        private int firstPoint = 0;
        private int length = 1;

        public void setParameters(int firstPoint, int length) {
            if(firstPoint > 4 || length > 2){
                throw new RuntimeException("Not valid parameters");
            }
            this.firstPoint = firstPoint;
            this.length = length;
        }

        @Override
        public Character[] crossover(Character c1, Character c2) {
            Character[] characters = {c1.clone(), c2.clone()};
            for(int i = firstPoint; i <= firstPoint + length; i++) {
                characters[0].equipElement(c2.getEquipment()[i % 5], i % 5);
                characters[1].equipElement(c1.getEquipment()[i % 5], i % 5);
            }
            return characters;
        }

    },

    UNIFORM {

        private double probability = 0.5;

        public void setProbability(double p) {
            if(p < 0 || p > 1) {
                throw  new RuntimeException("Not valid parameters");
            }
            this.probability = p;
        }

        @Override
        public Character[] crossover(Character c1, Character c2) {
            Character[] characters = {c1.clone(), c2.clone()};
            for(int i = 0; i < 5; i++){
                double p = Rand.randDouble();
                if(probability > p){
                    characters[0].equipElement(c2.getEquipment()[i], i);
                    characters[1].equipElement(c1.getEquipment()[i], i);
                }
            }
            return characters;
        }
    };

    public static Character[] crossoverWrapper(Character c1, Character c2) {
        if (Rand.randDouble() > Configuration.crossoverP) {
            Character[] characters = {c1.clone(), c2.clone()};
            return characters;
        } else {
            return Configuration.crossoverMethod.crossover(c1, c2);
        }
    }

}
