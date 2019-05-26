package ar.edu.itba.sia.tpe.game;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EquipmentData {

    Map<Integer, Equipment> weapons;
    Map<Integer, Equipment> boots;
    Map<Integer, Equipment> helmets;
    Map<Integer, Equipment> gloves;
    Map<Integer, Equipment> breastplates;

    public EquipmentData() {
        weapons = parseEquipmentFile("testdata/armas.tsv");
        boots = parseEquipmentFile("testdata/botas.tsv");
        helmets = parseEquipmentFile("testdata/cascos.tsv");
        gloves = parseEquipmentFile("testdata/guantes.tsv");
        breastplates = parseEquipmentFile("testdata/pecheras.tsv");
    }

    private static Map<Integer, Equipment> parseEquipmentFile(String filename) {
        Map<Integer, Equipment> equipment = new HashMap<>();
        try {
            Scanner line = new Scanner(new File(filename));
            line.nextLine();
            while (line.hasNext()) {
                Equipment e = new Equipment(line.nextInt(), line.nextDouble(), line.nextDouble(), line.nextDouble(), line.nextDouble(), line.nextDouble());
                equipment.put(e.getId(), e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return equipment;
    }

}
