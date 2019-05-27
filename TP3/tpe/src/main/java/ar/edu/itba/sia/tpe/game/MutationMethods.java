package ar.edu.itba.sia.tpe.game;

import ar.edu.itba.sia.tpe.game.interfaces.Mutation;

import java.util.Random;

public enum MutationMethods implements Mutation {

    GEN_UNIFORM {
        @Override
        public void mutate(Character character) {
            int gene = Rand.randInt(5);
            mutateGene(character, gene);
        }
    },

    GEN_NOT_UNIFORM{
        @Override
        public void mutate(Character character) {
            int gene = Rand.randInt(5);
            mutateGene(character, gene);
        }
    },

    MULTIGEN_UNIFORM{
        @Override
        public void mutate(Character character) {
            for(int i = 0; i < 6; i++){
                if(0.5 > Rand.randDouble()){
                    mutateGene(character, i);
                }
            }
        }
    },

    MULTIGEN_NOT_UNIFORM{
        @Override
        public void mutate(Character character) {
            for(int i = 0; i < 6; i++){
                if(0.5 > Rand.randDouble()){
                    mutateGene(character, i);
                }
            }
        }
    };

    public static void mutateWrapper(Character character, int generations) {
        switch (Configuration.mutationMethod){
            case GEN_UNIFORM:
            case MULTIGEN_UNIFORM:
                if(Configuration.mutationP > Rand.randDouble()){
                    Configuration.mutationMethod.mutate(character);
                }
                break;
            case GEN_NOT_UNIFORM:
            case MULTIGEN_NOT_UNIFORM:
                if(probabilityMutation(generations) > Rand.randDouble()){
                    Configuration.mutationMethod.mutate(character);
                }
                break;
        }
    }

    private static double probabilityMutation(int generations){
        return 2000.0 / (2500.0 + 10 * generations);
    }

    private static void mutateGene(Character character, int gene) {
        if(gene == 5){
            character.setHeight(Rand.randDouble(1.3, 2.0));
        } else {
            int rand = Rand.randInt(EquipmentData.getEquipments()[gene].size() - 1);
            Equipment e = EquipmentData.getEquipments()[gene].get(rand);
            character.equipElement(e, rand);
        }
    }
}
