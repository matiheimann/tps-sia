package ar.edu.itba.sia.tpe.game;

import java.util.Random;

public enum CrossAssassin implements Crossover{

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
            Character[] assasins = {new Assasin(c1), new Assasin(c2)};
            assasins[0].equipElement(c2.getEquipment()[geneChange], geneChange);
            assasins[1].equipElement(c1.getEquipment()[geneChange], geneChange);
            return assasins;
        }
    },


    TWO_POINT_CROSS{

        private int firstPoint = 0;
        private int secondPoint = 1;

        public void setGeneChanges(int geneChanges1, int geneChanges2){
            if(geneChanges1 >= geneChanges2 && geneChanges2 > 4){
                throw new RuntimeException("Not valid gene");
            }
            this.firstPoint = geneChanges1;
            this.secondPoint = geneChanges2;
        }

        @Override
        public Character[] crossover(Character c1, Character c2) {
            Character[] assasins = {new Assasin(c1), new Assasin(c2)};
            for(int i = firstPoint; i <= secondPoint; i++) {
                assasins[0].equipElement(c2.getEquipment()[i], i);
                assasins[1].equipElement(c1.getEquipment()[i], i);
            }
            return assasins;
        }


    },

    ANULAR{

        private int firstPoint = 0;
        private int length = 1;

        public void setParameters(int firstPoint, int length){
            if(firstPoint > 4 || length > 2){
                throw new RuntimeException("Not valid parameters");
            }
            this.firstPoint = firstPoint;
            this.length = length;
        }

        @Override
        public Character[] crossover(Character c1, Character c2) {
            Character[] assasins = {new Assasin(c1), new Assasin(c2)};
            for(int i = firstPoint; i <= firstPoint + length; i++) {
                assasins[0].equipElement(c2.getEquipment()[i % 5], i % 5);
                assasins[1].equipElement(c1.getEquipment()[i % 5], i % 5);
            }
            return assasins;
        }

    },

    UNIFORM{

        private double probability = 0.5;
        Random r = new Random();

        public void setProbability(double p){
            if(p < 0 || p > 1) {
                throw  new RuntimeException("Not valid parameters");
            }
            this.probability = p;
        }

        @Override
        public Character[] crossover(Character c1, Character c2) {
            Character[] assasins = {new Assasin(c1), new Assasin(c2)};
            for(int i = 0; i < 5; i++){
                double p = r.nextDouble();
                if(probability > p){
                    assasins[0].equipElement(c2.getEquipment()[i], i);
                    assasins[1].equipElement(c1.getEquipment()[i], i);
                }
            }
            return assasins;
        }
    }

}
