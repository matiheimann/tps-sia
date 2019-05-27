package ar.edu.itba.sia.tpe.game;

import ar.edu.itba.sia.tpe.game.interfaces.Crossover;

public enum CrossoverMethods implements Crossover {

    ONE_POINT_CROSS {

        @Override
        public Character[] crossover(Character c1, Character c2) {
            int geneChange = Rand.randInt(5);
            Character[] characters = {c1.clone(), c2.clone()};
            if(geneChange != 5) {
                characters[0].equipElement(c2.getEquipment()[geneChange], geneChange);
                characters[1].equipElement(c1.getEquipment()[geneChange], geneChange);
            }
            else{
                double aux = characters[0].getHeight();
                characters[0].setHeight(characters[1].getHeight());
                characters[1].setHeight(aux);
            }
            return characters;
        }
    },

    TWO_POINT_CROSS {

        @Override
        public Character[] crossover(Character c1, Character c2) {
            int firstPoint = Rand.randInt(4);
            int secondPoint = Rand.randInt(firstPoint, 5);
            Character[] characters = {c1.clone(), c2.clone()};
            for(int i = firstPoint; i <= Math.min(secondPoint, 4); i++) {
                characters[0].equipElement(c2.getEquipment()[i], i);
                characters[1].equipElement(c1.getEquipment()[i], i);
            }
            if(secondPoint == 5){
                double aux = characters[0].getHeight();
                characters[0].setHeight(characters[1].getHeight());
                characters[1].setHeight(aux);
            }
            return characters;
        }


    },

    ANULAR {

        @Override
        public Character[] crossover(Character c1, Character c2) {
            Character[] characters = {c1.clone(), c2.clone()};
            int firstPoint = Rand.randInt(5);
            int length = Rand.randInt(1, 4);
            for(int i = firstPoint; i < firstPoint + length; i++) {
                if(i != 5) {
                    characters[0].equipElement(c2.getEquipment()[i % 5], i % 5);
                    characters[1].equipElement(c1.getEquipment()[i % 5], i % 5);
                }
                else{
                    double aux = characters[0].getHeight();
                    characters[0].setHeight(characters[1].getHeight());
                    characters[1].setHeight(aux);
                }
            }
            return characters;
        }

    },


    UNIFORM {

        private double probability = 0.5;

        @Override
        public Character[] crossover(Character c1, Character c2) {
            Character[] characters = {c1.clone(), c2.clone()};
            double p;
            for(int i = 0; i < 5; i++){
                p = Rand.randDouble();
                if(probability > p){
                    characters[0].equipElement(c2.getEquipment()[i], i);
                    characters[1].equipElement(c1.getEquipment()[i], i);
                }
            }
            p = Rand.randDouble();
            if(probability > p){
                double aux = characters[0].getHeight();
                characters[0].setHeight(characters[1].getHeight());
                characters[1].setHeight(aux);
            }
            return characters;
        }
    };

    public static Character[] crossoverWrapper(Character c1, Character c2) {
        if (Configuration.crossoverP > Rand.randDouble()) {
            return Configuration.crossoverMethod.crossover(c1, c2);
        } else {
            Character[] characters = {c1.clone(), c2.clone()};
            return characters;
        }
    }

}
