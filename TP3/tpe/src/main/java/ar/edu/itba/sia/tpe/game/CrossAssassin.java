package ar.edu.itba.sia.tpe.game;

public enum CrossAssassin implements Cross{

    ONE_POINT_CROSS {

        private int geneChange;

        public void setGeneChange(int geneChange) {
            if (geneChange > 4) {
                throw new RuntimeException("Not valid gene");
            }
            this.geneChange = geneChange;
        }

        @Override
        public Character[] cross(Character c1, Character c2) {
            Character[] assasins = {new Assasin(c1), new Assasin(c2)};
            c1.equipElement(c2.getEquipment()[geneChange], geneChange);
            c2.equipElement(c1.getEquipment()[geneChange], geneChange);
            return assasins;
        }
    },


    TWO_POINT_CROSS{

        private int firstPoint;
        private int secondPoint;

        public void setGeneChanges(int geneChanges1, int geneChanges2){
            if(geneChanges1 >= geneChanges2 && geneChanges2 > 4){
                throw new RuntimeException("Not valid gene");
            }
            this.firstPoint = geneChanges1;
            this.secondPoint = geneChanges2;
        }

        @Override
        public Character[] cross(Character c1, Character c2) {
            Character[] assasins = {new Assasin(c1), new Assasin(c2)};
            for(int i = firstPoint; i <= secondPoint; i++) {
                c1.equipElement(c2.getEquipment()[i], i);
                c2.equipElement(c1.getEquipment()[i], i);
            }
            return assasins;
        }


    }

}
